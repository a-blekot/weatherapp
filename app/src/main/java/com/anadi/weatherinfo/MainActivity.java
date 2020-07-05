package com.anadi.weatherinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anadi.weatherinfo.addlocation.AddLocationActivity;

import timber.log.Timber;

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

        Timber.d("onCreate");
        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();

        ((CityAdapter)adapter).updateCities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("onPause");
        Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
        Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_SHORT).show();

        ((CityAdapter)adapter).updateCities();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Timber.d("onPostResume");
        Toast.makeText(getApplicationContext(), "onPostResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.d("onStart");
        Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.d("onStop");
        Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_SHORT).show();
    }

    public void addLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), AddLocationActivity.class);
        startActivity(intent);
    }
}