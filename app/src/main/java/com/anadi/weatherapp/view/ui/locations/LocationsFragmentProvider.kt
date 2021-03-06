package com.anadi.weatherapp.view.ui.locations

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationsFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideLocationsFragment(): LocationsFragment
}
