package com.juaristi.carmen.radiant;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        VideoView videoView = findViewById(R.id.video_view);
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        if (videoUrl != null) {
            videoView.setVideoURI(Uri.parse(videoUrl));
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            videoView.start();
        }
    }
}

