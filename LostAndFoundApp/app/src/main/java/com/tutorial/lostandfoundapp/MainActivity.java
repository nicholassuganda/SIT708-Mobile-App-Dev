package com.tutorial.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnAddNew, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNew = findViewById(R.id.btnNewAdvert);
        btnShowList = findViewById(R.id.btnShowList);

        btnAddNew.setOnClickListener(v -> {
            // Open CreateAdvertActivity
            startActivity(new Intent(MainActivity.this, CreateAdvertActivity.class));
        });

        btnShowList.setOnClickListener(v -> {
            // Open ItemListActivity
            startActivity(new Intent(MainActivity.this, ItemsListActivity.class));
        });
    }
}