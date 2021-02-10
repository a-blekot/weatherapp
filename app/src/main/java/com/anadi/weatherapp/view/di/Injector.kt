package com.anadi.weatherapp.view.di

import com.anadi.weatherapp.WeatherApplication

enum class Injector {

    INSTANCE;

    lateinit var applicationComponent: ApplicationComponent

    fun initialise(application: WeatherApplication) {
        applicationComponent = DaggerApplicationComponent.factory().create(application)
        applicationComponent.inject(application)
    }
}
