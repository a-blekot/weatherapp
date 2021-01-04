package com.anadi.weatherinfo.ui.addlocation

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AddLocationFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun provideAddLocationFragment(): AddLocationFragment
}