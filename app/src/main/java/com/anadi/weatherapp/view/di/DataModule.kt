package com.anadi.weatherapp.view.di

import android.content.Context
import com.anadi.weatherapp.data.db.AppDatabase
import com.anadi.weatherapp.data.db.location.LocationDao
import com.anadi.weatherapp.data.db.weather.WeatherDao
import com.anadi.weatherapp.data.location.LocationRepositoryImpl
import com.anadi.weatherapp.data.network.WeatherApi
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.data.network.suntime.SuntimeApi
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.data.weather.WeatherCodesImpl
import com.anadi.weatherapp.data.weather.WeatherRepositoriesImpl
import com.anadi.weatherapp.data.weather.WeatherRepositoryImpl
import com.anadi.weatherapp.domain.location.LocationRepository
import com.anadi.weatherapp.domain.places.PlacesWrapper
import com.anadi.weatherapp.domain.places.PlacesWrapperImpl
import com.anadi.weatherapp.domain.weather.WeatherRepositories
import com.anadi.weatherapp.domain.weather.WeatherRepository
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
