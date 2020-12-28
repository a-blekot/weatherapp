package com.anadi.weatherinfo.details

import androidx.annotation.StringRes
import com.anadi.weatherinfo.repository.data.WeatherInfo
import java.util.*

interface DetailsContract {
    interface View {
        fun loading()
        fun onError(@StringRes resId: Int)
        fun onUpdateSuccess()
    }

    interface Presenter : Observer {
        fun update()
        fun subscribe()
        fun unsubscribe()
        fun getInfo(id: Int): WeatherInfo?
    }

    interface Model {
        fun getInfo(id: Int): WeatherInfo?
        fun update(id: Int): Boolean
        fun needUpdate(id: Int): Boolean
    }
}