package com.anadi.weatherinfo

import android.app.Application
import androidx.work.*
import com.anadi.weatherinfo.view.di.Injector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

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

        Injector.INSTANCE.initialise(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}