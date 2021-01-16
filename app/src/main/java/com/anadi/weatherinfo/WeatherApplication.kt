package com.anadi.weatherinfo

import android.app.Application
import androidx.work.*
import com.anadi.weatherinfo.data.weather.WeatherCodes
import com.anadi.weatherinfo.view.di.Injector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("MagicNumber")
private const val UPDATE_INTERVAL_IN_MINUTES = 15L

class WeatherApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val updateRequest = PeriodicWorkRequest.Builder(
                UpdateWorker::class.java, UPDATE_INTERVAL_IN_MINUTES, TimeUnit.MINUTES
        )
                .setConstraints(constraints)
                .setInitialDelay(UPDATE_INTERVAL_IN_MINUTES, TimeUnit.SECONDS)
                .addTag("update_request10")
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                        "com.anadi.weatherinfo.update_request10", ExistingPeriodicWorkPolicy.KEEP, updateRequest
                )

        GlobalScope.launch(Dispatchers.Default) {
            WeatherCodes.init(this@WeatherApplication)
        }

        Injector.INSTANCE.initialise(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}
