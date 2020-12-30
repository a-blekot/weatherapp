package com.anadi.weatherinfo.ui.addlocation

import android.content.Context
import com.anadi.weatherinfo.repository.Country

interface LocationsProvider {
    fun loadLocations(context: Context)
    fun getCountryByName(countryName: String): Country
    fun getRandomCity(countryName: String): String
    fun getCityNames(country: String): List<String>

    val randomCountry: Country
    val countryNames: List<String>
}