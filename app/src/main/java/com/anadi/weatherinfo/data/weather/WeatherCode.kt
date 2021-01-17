package com.anadi.weatherinfo.data.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WeatherCode(
        val code: Int,
        @StringRes
        val description: Int,
        @DrawableRes
        val iconDay: Int,
        @DrawableRes
        val iconNight: Int
)
