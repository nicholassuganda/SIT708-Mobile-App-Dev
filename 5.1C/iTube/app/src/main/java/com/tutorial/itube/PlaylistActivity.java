package com.tutorial.itube;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements PlaylistAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private PlaylistDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        recyclerView = findViewById(R.id.playlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new PlaylistDatabaseHelper(this);
        List<String> videos = dbHelper.getAllVideos();

        adapter = new PlaylistAdapter(videos, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(String videoUrl) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("video_url", videoUrl);
        startActivity(intent);
        finish();
    }

}