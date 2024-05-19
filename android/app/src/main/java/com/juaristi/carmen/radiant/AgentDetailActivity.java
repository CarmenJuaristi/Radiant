package com.juaristi.carmen.radiant;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AgentDetailActivity extends AppCompatActivity {

    // Declaración del RecyclerView y el adaptador de videos
    private RecyclerView videosRecyclerView;
    private VideoAdapter videoAdapter;
    // Declaración del servicio API
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_detail);

        // Inicialización del RecyclerView y configuración del layout manager para disposición horizontal
        videosRecyclerView = findViewById(R.id.videos_recycler_view);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Inicialización del servicio API usando Retrofit
        apiService = RetrofitClient.getClient("https://valorant-api.com/").create(ApiService.class);

        // Obtención del nombre del agente desde el intent
        String agentName = getIntent().getStringExtra("AGENT_NAME");

        // Carga de los videos del agente
        loadAgentVideos(agentName);
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
                    videoAdapter = new VideoAdapter(AgentDetailActivity.this, videos);
                    // Configuración del adaptador en el RecyclerView
                    videosRecyclerView.setAdapter(videoAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AgentVideo>> call, Throwable t) {
                // Manejo del error en caso de fallo en la llamada
            }
        });
    }
}
