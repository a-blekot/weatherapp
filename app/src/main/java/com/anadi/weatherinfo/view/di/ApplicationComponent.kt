package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.WeatherApplication
import com.anadi.weatherinfo.view.ui.details.DetailsFragment
import com.anadi.weatherinfo.view.ui.locations.LocationsFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [
    ApplicationModule::class,
    DataModule::class,
    ApiModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class])
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: WeatherApplication): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(detailsFragment: DetailsFragment)
}
