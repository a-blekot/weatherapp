package com.anadi.weatherinfo.view.work

import android.content.Context
import androidx.work.*
import org.joda.time.DateTime
import org.joda.time.Minutes
import java.util.concurrent.TimeUnit

@Suppress("MagicNumber")
private const val UPDATE_INTERVAL_IN_HOURS = 6L
private const val UPDATE_MORNING_TIME = 7

object WorkManagerHelper {

    fun scheduleWork(context: Context) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val updateRequest = PeriodicWorkRequestBuilder<UpdateWorker>(UPDATE_INTERVAL_IN_HOURS, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(initialDelayInMinutes(), TimeUnit.MINUTES)
                .addTag("update_request10")
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "com.anadi.weatherinfo.update_request10",
                ExistingPeriodicWorkPolicy.KEEP,
                updateRequest
        )
    }

    private fun initialDelayInMinutes(): Long {
        val now = DateTime.now()
        val end = now.withTimeAtStartOfDay().plusDays(1).plusHours(UPDATE_MORNING_TIME)

        return Minutes.minutesBetween(now, end).minutes.toLong()
    }
}
