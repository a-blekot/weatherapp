package com.anadi.weatherinfo.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.WeatherApplication
import com.anadi.weatherinfo.ui.addlocation.AddLocationActivity
import com.anadi.weatherinfo.databinding.ActivityMainBinding
import com.anadi.weatherinfo.ui.details.DetailsActivity
import com.anadi.weatherinfo.ui.mainactivity.LocationAdapter.OnLocationSelectedListener
import com.anadi.weatherinfo.repository.IconMap
import com.anadi.weatherinfo.repository.LocationInfo
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), OnLocationSelectedListener, MainActivityContract.View {
    private val binding by viewBinding(ActivityMainBinding::bind, R.id.layout)
    
    private lateinit var adapter: LocationAdapter
    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    //    private UpdateReceiver mReceiver = new UpdateReceiver();
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_night_mode)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            item.setTitle(R.string.day_mode)
            item.setIcon(R.drawable.ic_day_mode)
        } else {
            item.setTitle(R.string.night_mode)
            item.setIcon(R.drawable.ic_night_mode)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_order -> {
                Toast.makeText(this, R.string.order_message, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_night_mode -> {
                val nightMode = AppCompatDelegate.getDefaultNightMode()
                // Set the theme mode for the restarted activity.
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                // Recreate the activity for the theme change to take effect.
                recreate()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun addLocation(view: View?) {
        val intent = Intent(this, AddLocationActivity::class.java)
        startActivity(intent)
    }

    override fun onSelected(locationInfo: LocationInfo) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("id", locationInfo.id)
        startActivity(intent)
    }

    override fun onMenuAction(locationInfo: LocationInfo, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_context_delete -> {
                presenter.deleteLocation(locationInfo)
                adapter.updateLocations()
            }
            R.id.menu_context_favorite -> {
            }
        }
    }

    override fun onUpdate() {
        Timber.d("2")
        adapter.updateLocations()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WeatherApplication.graph.inject(this)
        
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        IconMap.init(this)
        presenter.view = this
        presenter.loadLocations(this)
        adapter = LocationAdapter(this, this, presenter)

        binding.recyclerView.adapter = adapter

        Timber.d("onCreate")
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        presenter.loadData(this)
        presenter.subscribe()
        adapter.updateLocations()

        //        registerReceiver();
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.d("onDestroy")
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
        presenter.saveData(this)
        presenter.unsubscribe()
        //        unregisterReceiver(mReceiver);
        super.onDestroy()
    } //    private void registerReceiver() {
    //        IntentFilter filter = new IntentFilter();
    //        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
    //        filter.addAction(Intent.ACTION_POWER_CONNECTED);
    //        filter.addAction(UpdateReceiver.NOTIFICATION);
    //
    //        // Register the receiver using the activity context.
    //        registerReceiver(mReceiver, filter);
    //    }
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