package com.anadi.weatherinfo.data.weather

import android.content.Context
import com.anadi.weatherinfo.R

@Suppress("MagicNumber")
object WeatherCodes {
    private var inited = false

    private val allCodes = listOf(
            WeatherCode(200, R.string.thunderstorm_200, "t01d", "t01n"),
            WeatherCode(201, R.string.thunderstorm_201, "t02d", "t02n"),
            WeatherCode(202, R.string.thunderstorm_202, "t03d", "t03n"),
            WeatherCode(230, R.string.thunderstorm_230, "t04d", "t04n"),
            WeatherCode(231, R.string.thunderstorm_231, "t04d", "t04n"),
            WeatherCode(232, R.string.thunderstorm_232, "t04d", "t04n"),
            WeatherCode(233, R.string.thunderstorm_233, "t05d", "t05n"),

            WeatherCode(300, R.string.thunderstorm_300, "d01d", "d01n"),
            WeatherCode(301, R.string.thunderstorm_301, "d02d", "d02n"),
            WeatherCode(302, R.string.thunderstorm_302, "d03d", "d03n"),

            WeatherCode(500, R.string.thunderstorm_500, "r01d", "r01n"),
            WeatherCode(501, R.string.thunderstorm_501, "r02d", "r02n"),
            WeatherCode(502, R.string.thunderstorm_502, "r03d", "r03n"),
            WeatherCode(511, R.string.thunderstorm_511, "f01d", "f01n"),
            WeatherCode(520, R.string.thunderstorm_520, "r04d", "r04n"),
            WeatherCode(521, R.string.thunderstorm_521, "r05d", "r05n"),
            WeatherCode(522, R.string.thunderstorm_522, "r06d", "r06n"),

            WeatherCode(600, R.string.thunderstorm_600, "s01d", "s01n"),
            WeatherCode(601, R.string.thunderstorm_601, "s02d", "s02n"),
            WeatherCode(602, R.string.thunderstorm_602, "s03d", "s03n"),
            WeatherCode(610, R.string.thunderstorm_610, "s04d", "s04n"),
            WeatherCode(611, R.string.thunderstorm_611, "s05d", "s05n"),
            WeatherCode(612, R.string.thunderstorm_612, "s05d", "s05n"),
            WeatherCode(621, R.string.thunderstorm_621, "s01d", "s01n"),
            WeatherCode(622, R.string.thunderstorm_622, "s02d", "s02n"),
            WeatherCode(623, R.string.thunderstorm_623, "s06d", "s06n"),

            WeatherCode(700, R.string.thunderstorm_700, "a01d", "a01n"),
            WeatherCode(711, R.string.thunderstorm_711, "a02d", "a02n"),
            WeatherCode(721, R.string.thunderstorm_721, "a03d", "a03n"),
            WeatherCode(731, R.string.thunderstorm_731, "a04d", "a04n"),
            WeatherCode(741, R.string.thunderstorm_741, "a05d", "a05n"),
            WeatherCode(751, R.string.thunderstorm_751, "a06d", "a06n"),

            WeatherCode(800, R.string.thunderstorm_800, "c01d", "c01n"),
            WeatherCode(801, R.string.thunderstorm_801, "c02d", "c02n"),
            WeatherCode(802, R.string.thunderstorm_802, "c02d", "c02n"),
            WeatherCode(803, R.string.thunderstorm_803, "c03d", "c03n"),
            WeatherCode(804, R.string.thunderstorm_804, "c04d", "c04n"),

            WeatherCode(900, R.string.thunderstorm_900, "u00d", "u00n")
    )

    fun init(context: Context) {
        if (inited) {
            return
        }

        for (weatherCode in allCodes) {
            weatherCode.fetchIcons(context)
        }
        inited = true
    }

    fun fromCode(code: Int) = allCodes.firstOrNull { it.code == code } ?: allCodes.last()
}
