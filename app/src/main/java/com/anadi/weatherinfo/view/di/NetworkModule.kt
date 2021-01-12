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
@Suppress("UtilityClassWithPublicConstructor")
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
        @Named("OpenWeather")
        fun provideOkHttpClientOpenWeather(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideOkHttpClientWeatherbit(interceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
        }

        @Provides
        @Singleton
        @Named("OpenWeather")
        fun provideRetrofitOpenWeather(@Named("OpenWeather") client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideRetrofitWeatherbit(@Named("Weatherbit") client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                    .baseUrl("https://api.weatherbit.io/v2.0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }
    }
}
