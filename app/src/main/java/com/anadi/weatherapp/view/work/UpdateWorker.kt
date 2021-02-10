package com.anadi.weatherapp.view.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.anadi.weatherapp.domain.location.CheckUpdatesAllLocationsUseCase
import com.anadi.weatherapp.view.di.Injector
import com.anadi.weatherapp.view.ui.notification.WeatherNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UpdateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    @Inject
    lateinit var updatesAllLocationsUseCase: CheckUpdatesAllLocationsUseCase

    init {
        Injector.INSTANCE.applicationComponent.inject(this)
    }

    override fun doWork(): Result {
        val id = inputData.getInt("notification", 111)

        return try {
            Timber.d("UpdateWorker executed")
            CoroutineScope(Dispatchers.IO).launch {
                updatesAllLocationsUseCase.build(null)
                notificationManager.sendNotification("Weather updated", "Tap to see details", id)
            }
            Result.success()
        } catch (ex: IOException) {
            Timber.e(ex)
            Result.retry()
        }
    }
}
