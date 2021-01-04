package com.anadi.weatherinfo.di

import com.anadi.weatherinfo.WeatherApplication
import com.anadi.weatherinfo.ui.addlocation.AddLocationFragment
import com.anadi.weatherinfo.ui.details.DetailsFragment
import com.anadi.weatherinfo.ui.locations.LocationsFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(modules = [
    ApplicationModule::class,
    ViewModelModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    LocationProviderModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: WeatherApplication)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(addLocationFragment: AddLocationFragment)
    fun inject(detailsFragment: DetailsFragment)
}