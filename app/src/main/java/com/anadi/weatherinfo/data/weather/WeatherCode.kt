package com.anadi.weatherinfo.data.weather

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WeatherCode(
        val code: Int,
        @StringRes
        val description: Int, val iconDayName: String, val iconNightName: String
) {
    @DrawableRes
    var iconDay: Int = -1

    @DrawableRes
    var iconNight: Int = -1

    fun fetchIcons(context: Context) {
        iconDay = context.resources.getIdentifier(iconDayName, "drawable", context.packageName)
        iconNight = context.resources.getIdentifier(iconNightName, "drawable", context.packageName)
    }
}
