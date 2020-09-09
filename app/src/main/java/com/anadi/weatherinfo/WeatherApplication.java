package com.anadi.weatherinfo;

import android.app.Application;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class WeatherApplication extends Application {

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest updateRequest =
                new PeriodicWorkRequest.Builder(UpdateWorker.class, 5, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .addTag("update_request9")
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("com.anadi.weatherinfo.update_request9",
                ExistingPeriodicWorkPolicy.KEEP, updateRequest);
    }
}
