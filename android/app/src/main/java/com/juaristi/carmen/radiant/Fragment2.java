package com.juaristi.carmen.radiant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class Fragment2 extends Fragment {

    private RecyclerView recyclerView;
    private AgentsAdapter agentsAdapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_agents, container, false);

        // Inicializar el RecyclerView y configurarlo con un GridLayoutManager
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3)); // 3 columnas en la parrilla

        // Inicializar el servicio de API
        apiService = RetrofitClient.getClient("https://valorant-api.com/").create(ApiService.class);

        // Realizar la solicitud para obtener los agentes
        fetchAgents();

        return view; // Devolver la vista inflada
    }

    // Método para obtener los agentes de la API
    private void fetchAgents() {
        Call<AgentsResponse> call = apiService.getAgents();
        call.enqueue(new Callback<AgentsResponse>() {
            @Override
            public void onResponse(Call<AgentsResponse> call, Response<AgentsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener la lista de agentes de la respuesta
                    List<Agent> agentsList = response.body().getData();

                    // Configurar el adaptador con la lista de agentes
                    agentsAdapter = new AgentsAdapter(getContext(), agentsList);
                    recyclerView.setAdapter(agentsAdapter);
                } else {
                    // Manejo de respuesta fallida
                }
            }

            @Override
            public void onFailure(Call<AgentsResponse> call, Throwable t) {
                // Manejo de errores
            }

        });
    }
    private List<String> obtenerVideosDeAgente(String agentName) {
        // Implementa la lógica para obtener los videos de tu API
        // Por ejemplo, hacer una llamada HTTP a tu endpoint y parsear la respuesta
        return List.of("video1_url", "video2_url"); // Ejemplo estático
    }
}
