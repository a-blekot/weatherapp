package com.anadi.weatherinfo.view.work

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

@Suppress("MagicNumber")
private const val UPDATE_INTERVAL_IN_MINUTES = 15L
private const val INITIAL_DELAY_IN_MINUTES = 5L

object WorkManagerHelper {

    fun scheduleWork(context: Context) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val updateRequest = PeriodicWorkRequestBuilder<UpdateWorker>(UPDATE_INTERVAL_IN_MINUTES, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInitialDelay(INITIAL_DELAY_IN_MINUTES, TimeUnit.MINUTES)
                .addTag("update_request10")
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "com.anadi.weatherinfo.update_request10",
                ExistingPeriodicWorkPolicy.KEEP,
                updateRequest
        )
    }

//    private fun isWorkDone(context: Context, tag: String): Boolean {
//        val instance = WorkManager.getInstance(context)
//        val statuses = instance.getWorkInfosByTag(tag)
//        return statuses.isDone
//    }
}