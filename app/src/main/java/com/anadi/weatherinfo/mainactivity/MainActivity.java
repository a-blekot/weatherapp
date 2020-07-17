package com.anadi.weatherinfo.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.addlocation.AddLocationActivity;
import com.anadi.weatherinfo.repository.IconMap;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
//        }

        IconMap.init(this);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new LocationAdapter(this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Timber.d("onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        adapter.loadData();
        adapter.updateLocations();
    }

    public void addLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), AddLocationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();

        adapter.updateLocations();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

        adapter.saveData();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Timber.d("onPause");
//        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        Timber.d("onPostResume");
//        Toast.makeText(this, "onPostResume", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Timber.d("onStart");
//        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Timber.d("onStop");
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
//    }
}