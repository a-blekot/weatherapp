package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.view.ui.addlocation.AddLocationFragmentProvider
import com.anadi.weatherinfo.view.ui.details.DetailsFragmentProvider
import com.anadi.weatherinfo.view.ui.locations.LocationsFragmentProvider
import com.anadi.weatherinfo.view.ui.mainactivity.MainActivity
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