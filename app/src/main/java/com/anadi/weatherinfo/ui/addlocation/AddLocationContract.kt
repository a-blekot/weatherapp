package com.anadi.weatherinfo.ui.addlocation

import androidx.annotation.StringRes

interface AddLocationContract {
    interface View {
        fun loading()
        fun onError(@StringRes resId: Int)
        fun onAddedSuccess()
        fun updateCityList(cities: List<String>)
    }

    interface Presenter {
        fun setView(view: View)
        fun addLocation(cityName: String, countryName: String)
        fun onCountrySelected(countryName: String)
        val countryNames: List<String>
    }

    interface Model {
        fun add(cityName: String, countryName: String): Boolean
    }
}