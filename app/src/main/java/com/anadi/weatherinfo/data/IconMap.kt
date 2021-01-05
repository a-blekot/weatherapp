package com.anadi.weatherinfo.data

import android.content.Context
import java.util.*

object IconMap {
    private var inited = false
    private val icons: MutableMap<String, Int> = HashMap()
    private val iconNames = arrayOf("s01d", "s02d", "s03d", "s04d", "s09d", "s10d", "s11d", "s13d", "s50d",
            "s01n", "s02n", "s03n", "s04n", "s09n", "s10n", "s11n", "s13n", "s50n", "ic_no_icon")

    //            "01d", "02d", "03d", "04d", "09d",
    //            "10d", "11d", "13d", "50d",
    //            "01n", "02n", "03n", "04n", "09n",
    //            "10n", "11n", "13n", "50n"};
    fun init(context: Context) {
        if (inited) {
            return
        }
        var id: Int
        for (iconName in iconNames) {
            id = context.resources.getIdentifier(iconName, "drawable", context.packageName)
            icons[iconName] = id
        }
        inited = true
    }

    // choose icon with the same name as info.weather.icon
    fun getIconId(iconName: String): Int {
        return icons["s$iconName"] ?: icons.values.last()
    }
}