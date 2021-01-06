package com.anadi.weatherinfo.view.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class NetworkModule {

    companion object {

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        @Named("Weather")
        fun provideOkHttpClientWeather(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

//        @Provides
//        @JvmStatic
//        @Singleton
//        @Named("Stocks")
//        fun provideOkHttpClientStocks(interceptor: HttpLoggingInterceptor): OkHttpClient {
//            return OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .addNetworkInterceptor(StethoInterceptor())
//                .addInterceptor(StockApiInterceptor())
//                .build()
//        }

        @Provides
        @Singleton
        @Named("Weather")
        fun provideRetrofitWeather(@Named("Weather") client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

//        @Provides
//        @JvmStatic
//        @Singleton
//        @Named("Stocks")
//        fun provideRetrofitStocks(@Named("Stocks") client: OkHttpClient): Retrofit {
//            return Retrofit.Builder()
//                .baseUrl("http://api.marketstack.com/v1/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
//        }
    }
}