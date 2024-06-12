package com.juaristi.carmen.radiant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentDetailActivity extends AppCompatActivity {

    // Declaración del RecyclerView y el adaptador de videos
    private RecyclerView videosRecyclerView;
    private VideoAdapter videoAdapter;
    // Declaración del servicio API
    private ApiServiceVideos apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);

        // Inicialización del RecyclerView y configuración del layout manager para disposición horizontal
        videosRecyclerView = findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Obtención del nombre del agente desde el intent
        String agentName = getIntent().getStringExtra("AGENT_NAME");
        // Inicialización del servicio API usando Retrofit
        apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiServiceVideos.class);




        // Carga de los videos del agente
        loadAgentVideos(agentName);

        // Configuración del botón "Más info"
        Button moreInfoButton = findViewById(R.id.more_info_button);
        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentDetailActivity.this, AgentInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para cargar los videos del agente desde la API
    private void loadAgentVideos(String agentName) {
        // Realización de la llamada a la API
        apiService.getAgentVideos(agentName).enqueue(new Callback<List<AgentVideo>>() {
            @Override
            public void onResponse(Call<List<AgentVideo>> call, Response<List<AgentVideo>> response) {
                // Verificación de que la respuesta fue exitosa y no es nula
                if (response.isSuccessful() && response.body() != null) {
                    List<AgentVideo> videos = response.body();
                    // Inicialización del adaptador con los videos obtenidos
                    videoAdapter = new VideoAdapter(AgentDetailActivity.this, videos, new VideoAdapter.OnVideoClickListener() {
                        @Override
                        public void onVideoClick(AgentVideo video) {
                            Intent intent = new Intent (Intent.ACTION_VIEW);
                            String url = video.getVideo();
                            intent.setData(Uri.parse(url));
                            startActivity(intent);


                        }
                    });
                    // Configuración del adaptador en el RecyclerView
                    videosRecyclerView.setAdapter(videoAdapter);
                } else {
                    // Manejo de respuesta fallida
                    String message;
                    if (response.errorBody() != null) {
                        try {
                            message = response.errorBody().string();
                        } catch (IOException e) {
                            message = "Error en la respuesta.";
                            e.printStackTrace();
                        }
                    } else {
                        message = "Error en la respuesta.";
                    }
                    // Mostrar un Toast con el mensaje de error y el código de respuesta
                    Toast.makeText(AgentDetailActivity.this, message + " Código: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AgentVideo>> call, Throwable t) {
                // Manejo del error en caso de fallo en la llamada
                Toast.makeText(AgentDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
