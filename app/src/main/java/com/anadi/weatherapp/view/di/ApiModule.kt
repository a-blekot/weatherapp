package com.anadi.weatherapp.view.di

import com.anadi.weatherapp.data.network.WeatherApi
import com.anadi.weatherapp.data.network.openweather.OpenWeatherApi
import com.anadi.weatherapp.data.network.openweather.OpenWeatherApiFacade
import com.anadi.weatherapp.data.network.suntime.SuntimeApi
import com.anadi.weatherapp.data.network.weatherapi.WeatherapiApi
import com.anadi.weatherapp.data.network.weatherapi.WeatherapiApiFacade
import com.anadi.weatherapp.data.network.weatherbit.WeatherbitApi
import com.anadi.weatherapp.data.network.weatherbit.WeatherbitApiFacade
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@Suppress("UtilityClassWithPublicConstructor")
abstract class ApiModule {

    companion object {

        @Provides
        @Singleton
        fun provideSuntimeAPI(
                @Named("Suntime")
                retrofit: Retrofit
        ): SuntimeApi {
            return retrofit.create(SuntimeApi::class.java)
        }

        @Provides
        @Singleton
        fun provideOpenWeatherAPI(
                @Named("OpenWeather")
                retrofit: Retrofit
        ): OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)

        @Provides
        @Singleton
        fun provideWeatherbitAPI(
                @Named("Weatherbit")
                retrofit: Retrofit
        ): WeatherbitApi {
            return retrofit.create(WeatherbitApi::class.java)
        }

        @Provides
        @Singleton
        fun provideWeatherapiAPI(
                @Named("Weatherapi")
                retrofit: Retrofit
        ): WeatherapiApi {
            return retrofit.create(WeatherapiApi::class.java)
        }

        @Provides
        @Singleton
        @Named("OpenWeather")
        fun provideOpenWeatherApiFacade(openWeatherApi: OpenWeatherApi): WeatherApi {
            return OpenWeatherApiFacade(openWeatherApi)
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideWeatherbitApiFacade(weatherbitApi: WeatherbitApi): WeatherApi {
            return WeatherbitApiFacade(weatherbitApi)
        }

        @Provides
        @Singleton
        @Named("Weatherapi")
        fun provideWeatherapiApiFacade(weatherapiApi: WeatherapiApi): WeatherApi {
            return WeatherapiApiFacade(weatherapiApi)
        }
    }
}
