package com.anadi.weatherapp.view.di

import android.content.Context
import com.anadi.weatherapp.data.db.AppDatabase
import com.anadi.weatherapp.data.db.location.LocationDao
import com.anadi.weatherapp.data.db.user.UserDao
import com.anadi.weatherapp.data.db.weather.WeatherDao
import com.anadi.weatherapp.data.location.LocationRepositoryImpl
import com.anadi.weatherapp.data.network.WeatherApi
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.data.network.suntime.SuntimeApi
import com.anadi.weatherapp.data.user.UserRepositoryImpl
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.data.weather.WeatherCodesImpl
import com.anadi.weatherapp.data.weather.WeatherRepositoriesImpl
import com.anadi.weatherapp.data.weather.WeatherRepositoryImpl
import com.anadi.weatherapp.domain.location.LocationRepository
import com.anadi.weatherapp.domain.places.PlacesWrapper
import com.anadi.weatherapp.domain.places.PlacesWrapperImpl
import com.anadi.weatherapp.domain.user.UserRepository
import com.anadi.weatherapp.domain.weather.WeatherRepositories
import com.anadi.weatherapp.domain.weather.WeatherRepository
import com.google.firebase.database.*
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
        fun provideUserDao(appDatabase: AppDatabase): UserDao {
            return appDatabase.userDao()
        }

        @Provides
        @Singleton
        fun providePlacesWrapper(context: Context): PlacesWrapper {
            return PlacesWrapperImpl(context)
        }

        @Provides
        @Singleton
        fun provideUserRepository(
                @Named("Users") fbUsers: DatabaseReference,
                userDao: UserDao
        ): UserRepository {
            return UserRepositoryImpl(fbUsers, userDao)
        }

        @Provides
        @Singleton
        fun provideLocationRepository(
                @Named("Locations") fbLocations: DatabaseReference,
                locationDao: LocationDao,
                suntimeApi: SuntimeApi,
                networkMonitor: NetworkMonitor): LocationRepository {
            return LocationRepositoryImpl(fbLocations, locationDao, suntimeApi, networkMonitor)
        }

        @Provides
        @Singleton
        @Named("OpenWeather")
        fun provideOpenWeatherRepository(
                @Named("Locations") fbLocations: DatabaseReference,
                networkMonitor: NetworkMonitor,
                @Named("OpenWeather")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(fbLocations, networkMonitor, weatherApi, weatherDao)
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideWeatherbitRepository(
                @Named("Locations") fbLocations: DatabaseReference,
                networkMonitor: NetworkMonitor,
                @Named("Weatherbit")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(fbLocations, networkMonitor, weatherApi, weatherDao)
        }

        @Provides
        @Singleton
        @Named("Weatherapi")
        fun provideWeatherapiRepository(
                @Named("Locations") fbLocations: DatabaseReference,
                networkMonitor: NetworkMonitor,
                @Named("Weatherapi")
                weatherApi: WeatherApi, weatherDao: WeatherDao
        ): WeatherRepository {
            return WeatherRepositoryImpl(fbLocations, networkMonitor, weatherApi, weatherDao)
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
