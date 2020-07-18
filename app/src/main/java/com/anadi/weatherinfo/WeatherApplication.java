package com.anadi.weatherinfo;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
