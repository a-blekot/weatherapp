package com.anadi.weatherinfo

import android.app.Application
import androidx.work.*
import com.anadi.weatherinfo.di.AndroidModule
import com.anadi.weatherinfo.di.AppComponent
import com.anadi.weatherinfo.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit

class WeatherApplication : Application() {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val updateRequest = PeriodicWorkRequest.Builder(UpdateWorker::class.java, 5, TimeUnit.SECONDS).setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .addTag("update_request9")
                .build()
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork("com.anadi.weatherinfo.update_request9", ExistingPeriodicWorkPolicy.KEEP, updateRequest)

        graph = DaggerAppComponent.builder().androidModule(AndroidModule(this)).build();
        graph.inject(this)
    }

    companion object {
        lateinit var graph: AppComponent
    }
}