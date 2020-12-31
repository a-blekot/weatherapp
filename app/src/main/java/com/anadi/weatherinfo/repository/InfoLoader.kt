package com.anadi.weatherinfo.repository

import com.anadi.weatherinfo.repository.data.OpenWeatherMapInterface
import com.anadi.weatherinfo.repository.data.WeatherInfo
import com.anadi.weatherinfo.utils.WeatherException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException
import kotlin.jvm.Throws

class InfoLoader {
    private val mLang = "en"
    private val mRetrofit: Retrofit

    @Throws(WeatherException::class)
    fun load(cityName: String, country: Country): WeatherInfo {

        val location = cityName + "," + country.code.toLowerCase()

        try {
            Timber.d("Trying to load weather info for: %s", location)
            Timber.d("TEST = %s", TEST)
            val apiService = mRetrofit.create(OpenWeatherMapInterface::class.java)
            val call = apiService.getWeather(location, API_KEY, mUnits, mLang)
            val response = call!!.execute()
            val statusCode = response.code()
            val weatherInfo = response.body() ?: throw WeatherException("response body is empty for $location")

            Timber.d("Call (%s) statusCode = %d", location, statusCode)
            // код 200
            if (response.isSuccessful) {
                Timber.d("Yeah baby!!!")
            } else {
                when (statusCode) {
                    404 -> {
                    }
                    500 -> {
                    }
                }
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    Timber.d("Error message: %s", errorBody.string())
                }
            }
            Timber.d("Call (%s) statusCode = %d", location, statusCode)
            return weatherInfo
        } catch (e: IOException) {
            throw WeatherException("can't load $location", e)
        }
    }

    companion object {
        private const val API_URL = "http://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "f9dee5683fdf51c7b611df7f57f26926"
        private const val mUnits = "metric"
        private const val TEST = API_URL + "weather/?q=Kiev,ua&appid=" + API_KEY + "&units=" + mUnits
    }

    // Singleton with double check
    init {
        mRetrofit = Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }
}