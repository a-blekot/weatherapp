package com.anadi.weatherinfo.ui.details

import android.os.Handler
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.LocationInfo
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.repository.data.WeatherInfo
import com.anadi.weatherinfo.utils.WeatherException
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DetailsPresenter @Inject constructor(private val locationsCash: LocationsCash) : DetailsContract.Presenter {
    override lateinit var view: DetailsContract.View
    override var id: Int = 0
    private val handler = Handler()

    override fun update() {
        if (!locationsCash.needUpdate(id)) {
            Timber.d("No need for update. Data is fresh id = %d", id)
            return
        }
        view.loading()
        Thread(Runnable {
            try {
                locationsCash.update(id)
                onUpdated()
            } catch (e: WeatherException) {
                Timber.e(e)
                onError()
            }
        }).start()
    }

    override fun subscribe() {
        locationsCash.addObserver(this)
    }

    override fun unsubscribe() {
        locationsCash.deleteObserver(this)
    }

    override fun getInfo(id: Int): WeatherInfo? {
        return locationsCash.getInfo(id)
    }

    override fun update(o: Observable, arg: Any?) {
        if (arg is LocationInfo && arg.id == id) {
            onUpdated()
        }
    }

    private fun onUpdated() {
        handler.post { view.onUpdateSuccess() }
    }

    private fun onError() {
        handler.post { view.onError(R.string.on_error_update) }
    }

    init {
        onUpdated()
    }
}