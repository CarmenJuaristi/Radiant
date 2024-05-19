package com.juaristi.carmen.radiant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    // Contexto de la aplicación y lista de videos
    private Context context;
    private List<AgentVideo> videos;

    // Constructor que inicializa el contexto y la lista de videos
    public VideoAdapter(Context context, List<AgentVideo> videos) {
        this.context = context;
        this.videos = videos;
    }

    // Método para inflar el layout del item y crear el ViewHolder
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para el item del video
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    // Método para enlazar los datos del video con las vistas del ViewHolder
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // Obtención del video de la posición actual
        AgentVideo video = videos.get(position);
        // Configuración del texto del video
        holder.videoTextView.setText("Video " + (position + 1));

        // Utilizar Glide para cargar la imagen desde la URL
        Glide.with(context).load(video.getImage()).into(holder.videoThumbnail);

        // Configuración del click listener para abrir el video en el navegador
        holder.itemView.setOnClickListener(v -> {
            // Creación del intent para abrir el video en el navegador
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getVideo()));
            // Inicio de la actividad del navegador
            context.startActivity(intent);
        });
    }

    // Método para obtener la cantidad de items en la lista
    @Override
    public int getItemCount() {
        return videos.size();
    }

    // Clase ViewHolder para representar cada item de video
    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        // Declaración de las vistas del item
        ImageView videoThumbnail;
        TextView videoTextView;

        // Constructor que inicializa las vistas del item
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            videoTextView = itemView.findViewById(R.id.video_text);
        }
    }
}


