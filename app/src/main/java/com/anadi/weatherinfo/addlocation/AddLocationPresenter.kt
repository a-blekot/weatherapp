package com.anadi.weatherinfo.addlocation

import android.os.Handler
import android.text.TextUtils
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.Locations
import com.anadi.weatherinfo.repository.LocationsCash
import timber.log.Timber
import java.util.ArrayList
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AddLocationPresenter internal constructor(private val view: AddLocationContract.View) : AddLocationContract.Presenter {
    private val model: AddLocationContract.Model
    private val locations: LocationsProvider
    private val handler = Handler()
    private val exec: Executor
    override fun addLocation(selectedCity: String?, selectedCountry: String?) {
        view.loading()
        if (TextUtils.isEmpty(selectedCity) || TextUtils.isEmpty(selectedCountry) || selectedCity.equals(
                        "Select Item", ignoreCase = true) || selectedCountry
                        .equals("Select Item", ignoreCase = true)) {
            Timber.d("selectedCity: " + selectedCity + "selectedCountry: " + selectedCountry)
            view.onError(R.string.on_error_select_city)
            return
        }
        exec.execute {
            try {
                val result = model.add(selectedCity, selectedCountry)
                if (result) {
                    onCityAdded()
                } else {
                    onError()
                }
            } catch (e: Exception) {
                System.err.println(e.message)
                e.printStackTrace()
            }
        }
    }

    override fun onCountrySelected(countryName: String?) {
        view.updateCityList(locations.getCityNames(countryName)!!)
    }

    override val countryNames: ArrayList<String?>?
        get() = locations.countryNames

    private fun onCityAdded() {
        handler.post { view.onAddedSuccess() }
    }

    private fun onError() {
        handler.post { view.onError(R.string.on_error_select_city) }
    }

    init {
        model = LocationsCash.getInstance()
        locations = Locations.getInstance()
        exec = Executors.newSingleThreadExecutor()
    }
}