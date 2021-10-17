package com.anadi.weatherapp.view.di

import android.content.Context
import com.anadi.weatherapp.WeatherApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: WeatherApplication): Context {
        return application
    }
}
