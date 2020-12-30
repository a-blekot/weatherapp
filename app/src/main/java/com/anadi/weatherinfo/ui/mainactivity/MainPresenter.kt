package com.anadi.weatherinfo.ui.mainactivity

import android.content.Context
import android.os.Handler
import com.anadi.weatherinfo.repository.LocationInfo
import com.anadi.weatherinfo.repository.LocationsCash
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainPresenter @Inject constructor (private val locationsCash: LocationsCash) : MainActivityContract.Presenter {
    override lateinit var view: MainActivityContract.View
    private val handler = Handler()

    override fun loadLocations(context: Context) {
        locationsCash.loadLocations(context)
    }

    override fun saveData(context: Context) {
        locationsCash.saveData(context)
    }

    override fun loadData(context: Context) {
        locationsCash.loadData(context)
    }

    override fun subscribe() {
        locationsCash.addObserver(this)
    }

    override fun unsubscribe() {
        locationsCash.deleteObserver(this)
    }

    override val locations: ArrayList<LocationInfo>
        get() = locationsCash.locations

    override fun deleteLocation(locationInfo: LocationInfo): Boolean {
        return locationsCash.deleteLocation(locationInfo)
    }

    override fun update(o: Observable, arg: Any) {
        Timber.d("1")
        handler.post { view.onUpdate() }
    }
}