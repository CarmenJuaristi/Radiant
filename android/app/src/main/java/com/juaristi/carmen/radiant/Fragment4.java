package com.juaristi.carmen.radiant;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment4 extends Fragment {
    private EditText video;
    private EditText imagen;
    private EditText agente;
    private VideoAdapter videoAdapter;
    private RecyclerView videosrecyclerView;
    private ApiServiceVideos apiService;


    private RequestQueue queue;

    private FloatingActionButton buttonVideo;
    public interface OnVideoSelected2Listener{
        void onVideoSelected2(String videoUrl, String imageUrl, String agentName);

        void onVideoSelected2(String videoName);
    }
    private Fragment4.OnVideoSelected2Listener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (Fragment4.OnVideoSelected2Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnVideoSelectedListener");
        }
    }

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializamos el RecyclerView y lo configuramos con un GridLayoutManager
        videosrecyclerView = view.findViewById(R.id.videos_recycler_view);
        videosrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializamos el servicio de API
        apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiServiceVideos.class);
        fetchVideos();



        // Inflaremos el layout del fragmento

        buttonVideo = view.findViewById(R.id.button_open_dialog);
        buttonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(getContext());
                myBuilder.setView(inflateDialog());
                myBuilder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        publicVideo();
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });
        return view;
    }

    private View inflateDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.update_video_dialog, null);
        video = inflatedView.findViewById(R.id.video);
        imagen = inflatedView.findViewById(R.id.imagen);
        agente = inflatedView.findViewById(R.id.agente);
        return inflatedView;
    }
    private void publicVideo() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Session-Token", MODE_PRIVATE);
        String sessionToken = preferences.getString("sessionToken", null);

        if (sessionToken == null) {
            Toast.makeText(getContext(), "No se encontró el token de sesión.", Toast.LENGTH_SHORT).show();
            return;
        }

        UploadVideoRequestBody requestBody = new UploadVideoRequestBody();
        requestBody.setVideo_url(video.getText().toString());
        requestBody.setImage_url(imagen.getText().toString());
        requestBody.setAgent_name(agente.getText().toString());

        ApiServiceVideos apiServiceVideos = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiServiceVideos.class);
        Call<Void> call = apiServiceVideos.postUserVideo(sessionToken, requestBody);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Video publicado exitosamente.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al publicar el video.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchVideos() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Session-Token", MODE_PRIVATE);
        String sessionToken = preferences.getString("sessionToken", null);
        Call<List<AgentVideo>> call = apiService.getUserVideos(sessionToken);
        call.enqueue(new Callback<List<AgentVideo>>() {
            @Override
            public void onResponse(Call<List<AgentVideo>> call, Response<List<AgentVideo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AgentVideo> agentVideoList = response.body();
                    // Configuramos el adaptador con la lista de videos
                    videoAdapter = new VideoAdapter(getContext(), agentVideoList, new VideoAdapter.OnVideoClickListener() {
                        @Override
                        public void onVideoClick(AgentVideo video) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            String url = video.getVideo();
                            intent.setData(Uri.parse(url));
                            startActivity(intent);

                        }
                    });
                    videosrecyclerView.setAdapter(videoAdapter);
                } else {
                    // Manejamos en caso de respuesta fallida
                    String errorMessage = "Error: ";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += response.errorBody().string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        errorMessage += "Response body is empty.";
                    }
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AgentVideo>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    }

