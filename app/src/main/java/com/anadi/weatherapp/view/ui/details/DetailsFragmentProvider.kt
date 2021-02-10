package com.anadi.weatherapp.view.ui.details

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailsFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideDetailsFragment(): DetailsFragment
}
