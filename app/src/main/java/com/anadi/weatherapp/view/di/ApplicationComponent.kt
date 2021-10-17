package com.anadi.weatherapp.view.di

import com.anadi.weatherapp.WeatherApplication
import com.anadi.weatherapp.view.ui.details.DetailsFragment
import com.anadi.weatherapp.view.ui.locations.LocationsFragment
import com.anadi.weatherapp.view.ui.notification.WeatherMessagingService
import com.anadi.weatherapp.view.work.UpdateWorker
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            ApplicationModule::class,
            DataModule::class,
            FirebaseModule::class,
            ApiModule::class,
            NetworkModule::class,
            ViewModelModule::class,
            AndroidInjectionModule::class,
            ActivityBuilder::class
        ]
)
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: WeatherApplication): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(worker: UpdateWorker)
    fun inject(weatherMessagingService: WeatherMessagingService)
}
