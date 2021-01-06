package com.anadi.weatherinfo.view.ui.addlocation

import com.anadi.weatherinfo.data.db.location.Country

interface LocationsProvider {
    fun loadLocations()
    fun getCountryByName(countryName: String): Country
    fun getRandomCity(countryName: String): String
    fun getCityNames(country: String): List<String>

    val randomCountry: Country
    val countryNames: List<String>
}