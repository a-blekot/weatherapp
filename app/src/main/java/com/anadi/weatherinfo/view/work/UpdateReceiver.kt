package com.anadi.weatherinfo.view.work

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.anadi.weatherinfo.domain.location.UpdateAllLocationsUseCase
import com.anadi.weatherinfo.utils.WeatherException
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UpdateReceiver @Inject constructor(
        private val updateAllLocationsUseCase: UpdateAllLocationsUseCase
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //        String intentAction = intent.getAction();
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        if (id == 0) {
            Toasty.info(context, "Add random location", Toast.LENGTH_SHORT).show()

            GlobalScope.launch {
                try {
                    updateAllLocationsUseCase.build(null)

                    val mainIntent = Intent().apply {
                        setClassName(
                                "com.anadi.weatherinfo",
                                "com.anadi.weatherinfo.view.ui.mainactivity.MainActivity"
                        )
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    context.startActivity(mainIntent)

                } catch (e: WeatherException) {
                    Timber.e(e)
                }
            }
        }
    }

    companion object {
        const val NOTIFICATION_ID = "notification-id"
        const val NOTIFICATION = "notification"
    }
}
