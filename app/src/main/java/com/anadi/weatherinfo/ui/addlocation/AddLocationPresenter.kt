package com.anadi.weatherinfo.ui.addlocation

import android.location.LocationProvider
import android.os.Handler
import android.text.TextUtils
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.LocationsProvideImpl
import com.anadi.weatherinfo.repository.LocationsCash
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

class AddLocationPresenter @Inject constructor(private val locationCash: LocationsCash,
                                               private val locationsProvider: LocationsProvider) : AddLocationContract.Presenter {
    private lateinit var view: AddLocationContract.View
    private val handler = Handler()
    private val exec: Executor

    override fun setView(view: AddLocationContract.View) {
        this.view = view
    }

    override fun addLocation(cityName: String, countryName: String) {
        view.loading()
        if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(countryName) || cityName.equals(
                        "Select Item", ignoreCase = true) || countryName
                        .equals("Select Item", ignoreCase = true)) {
            Timber.d("selectedCity: " + cityName + "selectedCountry: " + countryName)
            view.onError(R.string.on_error_select_city)
            return
        }
        exec.execute {
            try {
                val result = locationCash.add(cityName, countryName)
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

    override fun onCountrySelected(countryName: String) {
        view.updateCityList(locationsProvider.getCityNames(countryName))
    }

    override val countryNames: List<String>
        get() = locationsProvider.countryNames

    private fun onCityAdded() {
        handler.post { view.onAddedSuccess() }
    }

    private fun onError() {
        handler.post { view.onError(R.string.on_error_select_city) }
    }

    init {
        exec = Executors.newSingleThreadExecutor()
    }
}