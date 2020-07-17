package com.anadi.weatherinfo.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.addlocation.AddLocationActivity;
import com.anadi.weatherinfo.details.DetailsActivity;
import com.anadi.weatherinfo.repository.IconMap;
import com.anadi.weatherinfo.repository.LocationInfo;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements LocationAdapter.OnLocationSelectedListener{

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
//        }

        IconMap.init(this);

        recyclerView = findViewById(R.id.recycler_view);
        presenter = new MainPresenter(this);
        presenter.loadLocations(this);

        adapter = new LocationAdapter(this, this, presenter);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Timber.d("onCreate");
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        presenter.loadData(this);
        adapter.updateLocations();
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

        presenter.saveData(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_order:
                Toast.makeText(this, R.string.order_message, Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addLocation(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSelected(LocationInfo locationInfo) {
        if (locationInfo == null) {
            Timber.wtf("locationInfo == null onSelected");
            return;
        }
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("id", locationInfo.getId());
        startActivity(intent);
    }

    @Override
    public void onMenuAction(LocationInfo locationInfo, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_context_delete:
                presenter.deleteLocation(locationInfo);
                adapter.updateLocations();
                break;
            case R.id.menu_context_favorite:

                break;
        }
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