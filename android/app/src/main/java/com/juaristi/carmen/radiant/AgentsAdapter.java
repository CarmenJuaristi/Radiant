package com.juaristi.carmen.radiant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsAdapter extends RecyclerView.Adapter<AgentsAdapter.AgentViewHolder> {

    // Contexto de la aplicación y lista de agentes
    private Context context;
    private List<Agent> agentsList;
    // Servicio API para realizar las solicitudes
    private ApiService apiService;

    // Constructor que inicializa el contexto, la lista de agentes y el servicio API
    public AgentsAdapter(Context context, List<Agent> agentsList) {
        this.context = context;
        this.agentsList = agentsList;
        this.apiService = RetrofitClient.getClient("https://valorant-api.com/").create(ApiService.class);
    }

    // Método para inflar el layout del item y crear el ViewHolder
    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_item, parent, false);
        return new AgentViewHolder(view);
    }

    // Método para enlazar los datos del agente con las vistas del ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position) {
        // Obtención del agente de la posición actual
        Agent agent = agentsList.get(position);
        // Carga de la imagen del agente usando Glide
        Glide.with(context).load(agent.getDisplayIconSmall()).into(holder.iconImageView);
        // Configuración del nombre del agente en el TextView
        holder.nameTextView.setText(agent.getDisplayName());

        // Realizar la solicitud para obtener los videos del agente
        apiService.getAgentVideos(agent.getDisplayName()).enqueue(new Callback<List<AgentVideo>>() {
            @Override
            public void onResponse(Call<List<AgentVideo>> call, Response<List<AgentVideo>> response) {
                // Verificación de que la respuesta fue exitosa y no es nula
                if (response.isSuccessful() && response.body() != null) {
                    // Obtención de la lista de videos
                    List<AgentVideo> videos = response.body();
                    // Creación y configuración del adaptador de videos
                    VideoAdapter videoAdapter = new VideoAdapter(context, videos);
                    holder.videosRecyclerView.setAdapter(videoAdapter);
                    holder.videosRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                }
            }

            @Override
            public void onFailure(Call<List<AgentVideo>> call, Throwable t) {
                // Manejo de errores en la solicitud
            }
        });

        // Configuración del click listener para el item del agente
        holder.itemView.setOnClickListener(v -> {
            // Creación del intent para abrir AgentDetailActivity
            Intent intent = new Intent(context, AgentDetailActivity.class);
            // Paso de datos del agente a la actividad de detalle
            intent.putExtra("AGENT_NAME", agent.getDisplayName());
            intent.putExtra("AGENT_IMAGE", agent.getDisplayIconSmall());
            // Inicio de la actividad de detalle
            context.startActivity(intent);
        });
    }

    // Método para obtener la cantidad de items en la lista
    @Override
    public int getItemCount() {
        return agentsList.size();
    }

    // Clase ViewHolder para representar cada item de agente
    public static class AgentViewHolder extends RecyclerView.ViewHolder {

        // Declaración de las vistas del item
        ImageView iconImageView;
        TextView nameTextView;
        RecyclerView videosRecyclerView;

        // Constructor que inicializa las vistas del item
        public AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.agent_icon);
            nameTextView = itemView.findViewById(R.id.agent_name);
            videosRecyclerView = itemView.findViewById(R.id.videos_recycler_view);
        }
    }
}
