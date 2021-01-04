package com.anadi.weatherinfo.di

import com.anadi.weatherinfo.repository.InfoLoader
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.repository.LocationsProviderImpl
import com.anadi.weatherinfo.ui.addlocation.LocationsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationProviderModule {

//    @Provides
//    fun createAddLocationPresenter(locationsCash: LocationsCash, locationsProvider: LocationsProvider): AddLocationContract.Presenter =
//            AddLocationViewModel(locationsCash, locationsProvider)
//
//    @Provides
//    fun createDetailsPresenter(locationsCash: LocationsCash): DetailsContract.Presenter =
//            DetailsViewModel(locationsCash)
//
//    @Provides
//    fun createMainPresenter(locationsCash: LocationsCash): MainActivityContract.Presenter =
//            LocationsViewModel(locationsCash)

    @Provides
    @Singleton
    fun createLocationsCash(locationsProvider: LocationsProvider, infoLoader: InfoLoader) = LocationsCash(locationsProvider, infoLoader)

    @Provides
    @Singleton
    fun createLocationsProvider(): LocationsProvider = LocationsProviderImpl()

    @Provides
    @Singleton
    fun provideInfoLoader() = InfoLoader()
}