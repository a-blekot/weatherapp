package com.anadi.weatherinfo.di

import com.anadi.weatherinfo.WeatherApplication
import com.anadi.weatherinfo.ui.addlocation.AddLocationActivity
import com.anadi.weatherinfo.ui.details.DetailsActivity
import com.anadi.weatherinfo.ui.mainactivity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AndroidModule::class, LocationProviderModule::class])
@Singleton
interface AppComponent {
    fun inject(application: WeatherApplication)
    fun inject(addLocationActivity: AddLocationActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(detailsActivity: DetailsActivity)

//    fun inject(locationsCash: LocationsCash)
//    fun inject(addLocationPresenter: AddLocationPresenter)
//    fun inject(updateReceiver: UpdateReceiver)
}