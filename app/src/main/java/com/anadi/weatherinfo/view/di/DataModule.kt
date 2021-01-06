package com.anadi.weatherinfo.view.di

import android.content.Context
import com.anadi.weatherinfo.data.LocationsProviderImpl
import com.anadi.weatherinfo.data.db.AppDatabase
import com.anadi.weatherinfo.data.db.location.LocationDao
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.location.LocationRepositoryImpl
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.weather.WeatherRepositoryImpl
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import com.anadi.weatherinfo.view.ui.addlocation.LocationsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
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
        fun provideLocationRepository(locationDao: LocationDao): LocationRepository {
            return LocationRepositoryImpl(locationDao)
        }

        @Provides
        @Singleton
        fun provideWeatherRepository(weatherApi: WeatherApi, weatherDao: WeatherDao, locationRepository: LocationRepository): WeatherRepository {
            return WeatherRepositoryImpl(weatherApi, weatherDao, locationRepository)
        }

        @Provides
        @Singleton
        fun createLocationsProvider(context: Context): LocationsProvider = LocationsProviderImpl(context)
    }
}