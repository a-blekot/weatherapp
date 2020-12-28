package com.anadi.weatherinfo.addlocation

import android.content.Context
import com.anadi.weatherinfo.repository.Country
import java.util.*

interface LocationsProvider {
    fun loadLocations(context: Context?)
    fun getCityNames(country: String?): ArrayList<String?>?
    val countryNames: ArrayList<String?>?
    fun getCountryByName(countryName: String?): Country?
    val randomCountry: Country?
    fun getRandomCity(countryName: String?): String?
}