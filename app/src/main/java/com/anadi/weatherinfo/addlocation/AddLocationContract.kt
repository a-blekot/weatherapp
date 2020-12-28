package com.anadi.weatherinfo.addlocation

import androidx.annotation.StringRes
import java.util.*

interface AddLocationContract {
    interface View {
        fun loading()
        fun onError(@StringRes resId: Int)
        fun onAddedSuccess()
        fun updateCityList(cities: ArrayList<String?>)
    }

    interface Presenter {
        fun addLocation(cityName: String?, countryName: String?)
        fun onCountrySelected(countryName: String?)
        val countryNames: ArrayList<String?>?
    }

    interface Model {
        fun add(cityName: String?, countryName: String?): Boolean
    }
}