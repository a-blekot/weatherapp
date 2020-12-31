package com.anadi.weatherinfo.ui.addlocation

import android.os.Handler
import android.text.TextUtils
import androidx.annotation.StringRes
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.utils.WeatherException
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
                locationCash.add(cityName, countryName)
                onCityAdded()
            } catch (e: WeatherException) {
                Timber.e(e)
                onError(R.string.on_error_load_location)
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

    private fun onError(@StringRes id: Int) {
        handler.post { view.onError(id) }
    }

    init {
        exec = Executors.newSingleThreadExecutor()
    }
}