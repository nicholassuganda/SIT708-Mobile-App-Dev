package com.tutorial.lostandfoundapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        dbHelper = new DatabaseHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Item> items = dbHelper.getAllItems();

        for (Item item : items) {
            LatLng location = new LatLng(item.getLatitude(), item.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title(item.getType() + ": " + item.getName())
                    .snippet(item.getDescription()));

            // Move camera to the first item
            if (items.indexOf(item) == 0) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
            }
        }
    }
}