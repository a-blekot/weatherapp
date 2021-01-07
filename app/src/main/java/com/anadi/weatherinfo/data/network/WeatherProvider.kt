package com.anadi.weatherinfo.data.network

enum class WeatherProvider constructor(val code: Int) {
    OPEN_WEATHER(1),
    WEATHER_BIT(2);

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