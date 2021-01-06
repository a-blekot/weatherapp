package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.WeatherApplication

enum class Injector {

    INSTANCE;

    lateinit var applicationComponent: ApplicationComponent

    fun initialise(application: WeatherApplication) {
        applicationComponent = DaggerApplicationComponent.factory()
                                                         .create(application)
        applicationComponent.inject(application)
    }
}