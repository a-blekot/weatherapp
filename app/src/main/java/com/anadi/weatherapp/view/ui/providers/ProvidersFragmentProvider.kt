package com.anadi.weatherapp.view.ui.providers

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProvidersFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideProvidersFragment(): ProvidersFragment
}
