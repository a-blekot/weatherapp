package com.anadi.weatherinfo.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideLocationManager(): LocationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    @Singleton
    @Named("something")
    fun provideSomething(): String = "something"

    @Provides
    @Singleton
    @Named("somethingElse")
    fun provideSomethingElse(): String = "somethingElse"

}