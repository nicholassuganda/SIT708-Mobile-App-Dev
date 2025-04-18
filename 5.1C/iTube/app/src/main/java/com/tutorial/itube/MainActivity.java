package com.tutorial.itube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private EditText videoUrlEditText;
    private Button playButton, addToPlaylistButton, myPlaylistButton, logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        videoUrlEditText = findViewById(R.id.video_url_edittext);
        playButton = findViewById(R.id.play_button);
        addToPlaylistButton = findViewById(R.id.add_to_playlist_button);
        myPlaylistButton = findViewById(R.id.my_playlist_button);
        logoutButton = findViewById(R.id.logout_button);


        getLifecycle().addObserver(youTubePlayerView);

        playButton.setOnClickListener(v -> playVideo());
        addToPlaylistButton.setOnClickListener(v -> addToPlaylist());
        myPlaylistButton.setOnClickListener(v -> openPlaylist());
        logoutButton.setOnClickListener(v -> logout());
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void playVideo() {
        String url = videoUrlEditText.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoId = extractVideoId(url);
        if (videoId == null) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
            youTubePlayer.loadVideo(videoId, 0);
        });
    }

    private void addToPlaylist() {
        String url = videoUrlEditText.getText().toString().trim();
        if (!url.isEmpty()) {
            PlaylistDatabaseHelper dbHelper = new PlaylistDatabaseHelper(this);
            dbHelper.addVideo(url);
        }
    }

    private void openPlaylist() {
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }

    private String extractVideoId(String url) {
        String videoId = null;
        if (url != null && url.trim().length() > 0) {
            if (url.contains("v=")) {
                videoId = url.substring(url.indexOf("v=") + 2);
                if (videoId.contains("&")) {
                    videoId = videoId.substring(0, videoId.indexOf("&"));
                }
            } else if (url.contains("youtu.be/")) {
                videoId = url.substring(url.indexOf("youtu.be/") + 9);
                if (videoId.contains("?")) {
                    videoId = videoId.substring(0, videoId.indexOf("?"));
                }
            }
        }
        return videoId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
}