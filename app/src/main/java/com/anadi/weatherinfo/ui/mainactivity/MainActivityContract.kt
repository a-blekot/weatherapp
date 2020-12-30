package com.anadi.weatherinfo.ui.mainactivity

import android.content.Context
import com.anadi.weatherinfo.repository.LocationInfo
import java.util.*

interface MainActivityContract {
    interface View {
        fun onUpdate()
    }

    interface Presenter : Observer {
        var view: View

        fun loadLocations(context: Context)
        fun saveData(context: Context)
        fun loadData(context: Context)
        fun subscribe()
        fun unsubscribe()
        val locations: ArrayList<LocationInfo>
        fun deleteLocation(locationInfo: LocationInfo): Boolean
    }

    interface Model {
        fun loadLocations(context: Context)
        fun saveData(context: Context)
        fun loadData(context: Context)
        val locations: ArrayList<LocationInfo>
        fun deleteLocation(locationInfo: LocationInfo): Boolean
    }
}