package com.tutorial.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        db = new DatabaseHelper(this);

        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        if (itemId == -1) {
            finish();
            return;
        }

        currentItem = db.getAllItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);

        if (currentItem == null) {
            finish();
            return;
        }

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewPhone = findViewById(R.id.textViewPhone);
        Button buttonRemove = findViewById(R.id.buttonRemove);

        textViewTitle.setText(currentItem.getType() + " " + currentItem.getName());
        textViewDate.setText(currentItem.getDate());
        textViewLocation.setText("At " + currentItem.getLocation());
        textViewDescription.setText(currentItem.getDescription());
        textViewPhone.setText("Contact: " + currentItem.getPhone());

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteItem(currentItem.getId());
                Toast.makeText(ItemDetailActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}