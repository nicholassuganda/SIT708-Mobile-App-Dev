package com.tutorial.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnAddNew, btnShowList, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNew = findViewById(R.id.btnNewAdvert);
        btnShowList = findViewById(R.id.btnShowList);
        btnShowMap = findViewById(R.id.btnShowMap);

        btnAddNew.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateAdvertActivity.class));
        });

        btnShowList.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ItemsListActivity.class));
        });

        btnShowMap.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        });
    }
}