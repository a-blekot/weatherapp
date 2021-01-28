package com.anadi.weatherinfo.data.weather

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.anadi.weatherinfo.data.db.location.Location

data class WeatherCode(
        val code: Int,
        @StringRes
        val description: Int,
        @DrawableRes
        private val iconDay: Int,
        @DrawableRes
        private val iconNight: Int
) {
        fun getIcon(location: Location) = if (location.isDayNow()) iconDay else iconNight
}
