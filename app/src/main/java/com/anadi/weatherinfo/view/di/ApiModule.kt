package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class ApiModule {

    companion object {

        @Provides
        @Singleton
        fun provideWeatherAPI(@Named("Weather") retrofit: Retrofit): WeatherApi {
            return retrofit.create(WeatherApi::class.java)
        }

//        @Provides
//        @JvmStatic
//        @Singleton
//        fun provideStockAPI(@Named("Stocks") retrofit: Retrofit): StockAPI {
//            return retrofit.create(StockAPI::class.java)
//        }
    }
}