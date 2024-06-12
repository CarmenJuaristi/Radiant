package com.juaristi.carmen.radiant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private List<AgentVideo> videoList;
    private OnVideoClickListener mListener;

    public interface OnVideoClickListener {
        void onVideoClick(AgentVideo video);
    }

    public VideoAdapter(Context context, List<AgentVideo> videoList, OnVideoClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AgentVideo video = videoList.get(position);
        holder.videoTitle.setText(video.getTitle());
        Glide.with(context)
                .load(video.getImage())
                .into(holder.videoThumbnail);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onVideoClick(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView videoTitle;
        public ImageView videoThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.video_text);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
        }
    }
}
