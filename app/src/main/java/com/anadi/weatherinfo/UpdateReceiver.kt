package com.anadi.weatherinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.repository.LocationsProviderImpl
import com.anadi.weatherinfo.ui.addlocation.LocationsProvider
import com.anadi.weatherinfo.utils.WeatherException
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class UpdateReceiver @Inject constructor(private val locationsCash: LocationsCash) : BroadcastReceiver() {
    private val locationsProvider: LocationsProvider = LocationsProviderImpl()
    private val exec: Executor = Executors.newSingleThreadExecutor()
    override fun onReceive(context: Context, intent: Intent) {
        //        String intentAction = intent.getAction();
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        if (id == 0) {
            Toasty.info(context, "Add random location", Toast.LENGTH_SHORT).show()
            val (name) = locationsProvider.randomCountry
            val cityName = locationsProvider.getRandomCity(name)
            exec.execute {
                try {
                    locationsCash.add(cityName, name)
                } catch (e: WeatherException) {
                    Timber.e(e)
                }
            }
            return
        }
    }

    companion object {
        const val NOTIFICATION_ID = "notification-id"
        const val NOTIFICATION = "notification"
    }
}