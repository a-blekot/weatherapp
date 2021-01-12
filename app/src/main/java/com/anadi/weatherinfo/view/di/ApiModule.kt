package com.anadi.weatherinfo.view.di

import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.openweather.OpenWeatherApi
import com.anadi.weatherinfo.data.network.openweather.OpenWeatherApiFacade
import com.anadi.weatherinfo.data.network.weatherbit.WeatherbitApi
import com.anadi.weatherinfo.data.network.weatherbit.WeatherbitApiFacade
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
        fun provideOpenWeatherAPI(@Named("OpenWeather") retrofit: Retrofit): OpenWeatherApi {
            return retrofit.create(OpenWeatherApi::class.java)
        }

        @Provides
        @Singleton
        fun provideWeatherbitAPI(@Named("Weatherbit") retrofit: Retrofit): WeatherbitApi {
            return retrofit.create(WeatherbitApi::class.java)
        }

        @Provides
        @Singleton
        @Named("OpenWeather")
        fun provideOpenWeatherApiFacade(openWeatherApi: OpenWeatherApi): WeatherApi{
            return OpenWeatherApiFacade(openWeatherApi)
        }

        @Provides
        @Singleton
        @Named("Weatherbit")
        fun provideWeatherbitApiFacade(weatherbitApi: WeatherbitApi): WeatherApi{
            return WeatherbitApiFacade(weatherbitApi)
        }
    }
}
