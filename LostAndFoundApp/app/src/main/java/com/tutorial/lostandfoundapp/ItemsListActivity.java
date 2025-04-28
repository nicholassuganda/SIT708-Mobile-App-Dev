package com.tutorial.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ItemsListActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        loadItems();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                Intent intent = new Intent(ItemsListActivity.this, ItemDetailActivity.class);
                intent.putExtra("ITEM_ID", item.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    private void loadItems() {
        List<Item> itemList = db.getAllItems();
        ItemAdapter adapter = new ItemAdapter(this, itemList);
        listView.setAdapter(adapter);
    }
}