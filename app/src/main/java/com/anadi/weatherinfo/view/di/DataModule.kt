package com.anadi.weatherinfo.view.di

import android.content.Context
import com.anadi.weatherinfo.data.db.AppDatabase
import com.anadi.weatherinfo.data.db.location.LocationDao
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.location.LocationRepositoryImpl
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.state.NetworkMonitor
import com.anadi.weatherinfo.data.network.suntime.SuntimeApi
import com.anadi.weatherinfo.data.weather.WeatherCodes
import com.anadi.weatherinfo.data.weather.WeatherCodesImpl
import com.anadi.weatherinfo.data.weather.WeatherRepositoriesImpl
import com.anadi.weatherinfo.data.weather.WeatherRepositoryImpl
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.places.PlacesWrapper
import com.anadi.weatherinfo.domain.places.PlacesWrapperImpl
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
@Suppress("UtilityClassWithPublicConstructor")
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideRoomDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        @Singleton
        fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
            return appDatabase.locationDao()
        }

        @Provides
        @Singleton
        fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
            return appDatabase.weatherDao()
        }

        @Provides
        @Singleton
        fun providePlacesWrapper(context: Context): PlacesWrapper {
            return PlacesWrapperImpl(context)
        }

        @Provides
        @Singleton
        fun provideLocationRepository(
                locationDao: LocationDao,
                suntimeApi: SuntimeApi,
                networkMonitor: NetworkMonitor): LocationRepository {
            return LocationRepositoryImpl(locationDao, suntimeApi, networkMonitor)
        }

        @Provides
        @Singleton
        @Named("OpenWeather")
        fun provideOpenWeatherRepository(
                networkMonitor: NetworkMonitor,
                @Named("OpenWeather")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(networkMonitor, weatherApi, weatherDao)
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideWeatherbitRepository(
                networkMonitor: NetworkMonitor,
                @Named("Weatherbit")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(networkMonitor, weatherApi, weatherDao)
        }

        @Provides
        @Singleton
        @Named("Weatherapi")
        fun provideWeatherapiRepository(
                networkMonitor: NetworkMonitor,
                @Named("Weatherapi")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(networkMonitor, weatherApi, weatherDao)
        }

        @Provides
        @Singleton
        fun provideWeatherRepositories(
                @Named("OpenWeather")
                openweather: WeatherRepository,
                @Named("Weatherbit")
                weatherbit: WeatherRepository,
                @Named("Weatherapi")
                weatherapi: WeatherRepository
        ): WeatherRepositories {
            return WeatherRepositoriesImpl(openweather, weatherbit, weatherapi)
        }

        @Provides
        @Singleton
        fun provideWeatherCodes(context: Context): WeatherCodes = WeatherCodesImpl(context)
    }
}
