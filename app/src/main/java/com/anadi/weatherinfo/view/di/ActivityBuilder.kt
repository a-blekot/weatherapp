package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.view.ui.details.DetailsFragmentProvider
import com.anadi.weatherinfo.view.ui.locations.LocationsFragmentProvider
import com.anadi.weatherinfo.view.ui.mainactivity.MainActivity
import com.anadi.weatherinfo.view.ui.providers.ProvidersFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
            modules = [LocationsFragmentProvider::class,
                ProvidersFragmentProvider::class,
                DetailsFragmentProvider::class]
    )
    internal abstract fun bindMainActivity(): MainActivity
}
