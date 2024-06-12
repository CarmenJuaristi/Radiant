package com.juaristi.carmen.radiant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juaristi.carmen.radiant.AgentVideo;
import com.juaristi.carmen.radiant.ApiServiceVideos;
import com.juaristi.carmen.radiant.RetrofitClient;
import com.juaristi.carmen.radiant.VideoAdapter;
import com.juaristi.carmen.radiant.VideoPlayerActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.juaristi.carmen.radiant.VideoAdapter.OnVideoClickListener;

public class Fragment1 extends Fragment{
    private TextView titulo;
    private ImageView logo;
    private VideoAdapter videoAdapter;
    private RecyclerView videosrecyclerView;
    private ApiServiceVideos apiService;


    public interface OnVideoSelectedListener{
        void onVideoSelectedListener(String videoName);
    }
    private Fragment1.OnVideoSelectedListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (Fragment1.OnVideoSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnVideoSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        // Inicializamos el RecyclerView y lo configuramos con un GridLayoutManager
        videosrecyclerView = view.findViewById(R.id.videos_recycler_view);
        videosrecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializamos el servicio de API
        apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiServiceVideos.class);
        fetchVideos();
        return view;
    }

    private void fetchVideos() {
        Call<List<AgentVideo>> call = apiService.getVideos();
        call.enqueue(new Callback<List<AgentVideo>>() {
            @Override
            public void onResponse(Call<List<AgentVideo>> call, Response<List<AgentVideo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AgentVideo> agentVideoList = response.body();
                    // Configuramos el adaptador con la lista de videos
                    videoAdapter = new VideoAdapter(getContext(), agentVideoList, Fragment1.this);
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

    // Implementación del método de la interfaz OnVideoClickListener
    @Override
    public void onVideoClick(AgentVideo video) {
        // Manejar el clic en el video para reproducirlo
        Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
        intent.putExtra("VIDEO_URL", video.getVideo());
        startActivity(intent);
    }
}
