package com.anadi.weatherinfo.details

import android.os.Handler
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.LocationInfo
import com.anadi.weatherinfo.repository.LocationsCash
import com.anadi.weatherinfo.repository.data.WeatherInfo
import timber.log.Timber
import java.util.*

class DetailsPresenter internal constructor(private val view: DetailsContract.View, id: Int) : DetailsContract.Presenter {
    private val model: DetailsContract.Model
    private val handler = Handler()
    private val id: Int
    override fun update() {
        if (!model.needUpdate(id)) {
            Timber.d("No need for update. Data is fresh id = %d", id)
            return
        }
        view.loading()
        Thread(Runnable {
            try {
                val result = model.update(id)
                if (result) {
                    onUpdated()
                } else {
                    onError()
                }
            } catch (e: Exception) {
                System.err.println(e.message)
                e.printStackTrace()
            }
        }).start()
    }

    override fun subscribe() {
        val modelObservable: Observable = LocationsCash.getInstance()
        modelObservable.addObserver(this)
    }

    override fun unsubscribe() {
        val modelObservable: Observable = LocationsCash.getInstance()
        modelObservable.deleteObserver(this)
    }

    override fun getInfo(id: Int): WeatherInfo? {
        return model.getInfo(id)
    }

    override fun update(o: Observable, arg: Any) {
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
        model = LocationsCash.getInstance()
        this.id = id
        onUpdated()
    }
}