package com.anadi.weatherapp.view.di

import com.anadi.weatherapp.view.ui.details.DetailsFragmentProvider
import com.anadi.weatherapp.view.ui.locations.LocationsFragmentProvider
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import com.anadi.weatherapp.view.ui.providers.ProvidersFragmentProvider
import com.anadi.weatherapp.view.ui.signin.SignInActivity
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

    @ContributesAndroidInjector
    internal abstract fun bindSignInActivity(): SignInActivity
}
