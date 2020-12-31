package com.anadi.weatherinfo.repository

import android.content.Context
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.ui.addlocation.LocationsProvider
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class LocationsProviderImpl : LocationsProvider {
    private val random = Random(System.currentTimeMillis())
    private val countries = ArrayList<Country>()
    private val cities: MutableMap<String, ArrayList<String>> = HashMap()

    override fun loadLocations(context: Context) {
        var jsonString = convert(context.resources.openRawResource(R.raw.countries_codes))
        Timber.d(jsonString);
        try {
            Timber.d("I`.m here!");
            val array = JSONArray(jsonString)
            var obj: JSONObject
            for (i in 0 until array.length()) {
                obj = array.getJSONObject(i)
                countries.add(Country(obj.getString("name"), obj.getString("code")))
            }

            Timber.d(countries.toString());
        } catch (e: JSONException) {
            Timber.e(e)
        }

        try {
            for (country in countries) {
                val resourceName = country.name.toLowerCase().replace(" ", "_")
                jsonString = convert(context.resources
                        .openRawResource(context.resources
                                .getIdentifier(resourceName, "raw",
                                        context.packageName)))
                val obj = JSONObject(jsonString)
                val array = obj.getJSONArray(country.name)
                if (array.length() == 0) {
                    //                    countries.remove(c);
                    Timber.d("There are no cities for country: %s", country.name)
                    continue
                }
                val cityArray = ArrayList<String>()
                for (i in 0 until array.length()) {
                    cityArray.add(array.getString(i))
                }
                cities[country.toString()] = cityArray
            }
        } catch (e: JSONException) {
            Timber.e(e)
        }
    }

    override fun getCountryByName(countryName: String) = countries.firstOrNull { it.name == countryName }
            ?: Country.EMPTY

    override fun getRandomCity(countryName: String): String {
        val cityNames = getCityNames(countryName)
        return if (cityNames.isEmpty()) "" else cityNames[random.nextInt(cityNames.size)]
    }

    override fun getCityNames(country: String) = cities[country]!!

    override val randomCountry = if (countries.isEmpty()) Country.EMPTY else countries[random.nextInt(countries.size)]

    override val countryNames: List<String>
        get() = countries.map { it.toString() }

    private fun convert(inputStream: InputStream): String {
        val stringBuilder = StringBuilder()
        var line: String?
        try {
            BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                    //                Timber.d( line);
                }
            }
        } catch (e: IOException) {
            Timber.e(e)
        }
        return stringBuilder.toString()
    }
}