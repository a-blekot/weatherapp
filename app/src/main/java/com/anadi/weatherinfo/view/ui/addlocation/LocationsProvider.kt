package com.anadi.weatherinfo.view.ui.addlocation

import android.content.Context
import com.anadi.weatherinfo.data.Country

interface LocationsProvider {
    fun loadLocations(context: Context)
    fun getCountryByName(countryName: String): Country
    fun getRandomCity(countryName: String): String
    fun getCityNames(country: String): List<String>

    val randomCountry: Country
    val countryNames: List<String>
}