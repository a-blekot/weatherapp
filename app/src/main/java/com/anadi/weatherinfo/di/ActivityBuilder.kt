package com.anadi.weatherinfo.di

import com.anadi.weatherinfo.ui.addlocation.AddLocationFragmentProvider
import com.anadi.weatherinfo.ui.details.DetailsFragmentProvider
import com.anadi.weatherinfo.ui.locations.LocationsFragmentProvider
import com.anadi.weatherinfo.ui.mainactivity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [
        LocationsFragmentProvider::class,
        DetailsFragmentProvider::class,
        AddLocationFragmentProvider::class]
    )
    internal abstract fun bindMainActivity(): MainActivity
}