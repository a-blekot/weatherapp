package com.anadi.weatherapp.data.network

@Suppress("MagicNumber")
enum class WeatherProvider constructor(val code: Int, val providerName: String) {
    OPEN_WEATHER(1, "OpenWeather"),
    WEATHER_BIT(2, "Weatherbit"),
    WEATHER_API(3, "Weatherapi"),
    MERGED(4, "Merged info");

    companion object {
        fun fromCode(code: Int): WeatherProvider {
            require(code in 1..values().size) {
                "WeatherProvider code should be in range 1..${values().size}. Got -> code = $code"
            }

            for (provider in values()) {
                if (provider.code == code) {
                    return provider
                }
            }

            return OPEN_WEATHER
        }
    }
}
