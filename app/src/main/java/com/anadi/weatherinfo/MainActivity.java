package com.anadi.weatherinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location.setContext(getApplicationContext());
        Location.loadLocations();

        recyclerView = findViewById(R.id.recycler_view);
//        adapter = new CityAdapter(getApplicationContext());
        adapter = new CityAdapter();
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Log.d("anadi_lifecycle", "onCreate");
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT);

        ((CityAdapter)adapter).updateCities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("anadi_lifecycle", "onDestroy");
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("anadi_lifecycle", "onPause");
        Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("anadi_lifecycle", "onResume");
        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT);

        ((CityAdapter)adapter).updateCities();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("anadi_lifecycle", "onPostResume");
        Toast.makeText(getApplicationContext(), "onPostResume", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("anadi_lifecycle", "onStart");
        Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("anadi_lifecycle", "onStop");
        Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT);
    }

    public void addLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), AddLocation.class);
        startActivity(intent);
    }
}