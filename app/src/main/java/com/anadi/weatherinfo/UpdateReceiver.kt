package com.anadi.weatherinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.anadi.weatherinfo.domain.location.AddLocationUseCase
import com.anadi.weatherinfo.utils.WeatherException
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class UpdateReceiver @Inject constructor(private val addLocationUseCase: AddLocationUseCase) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //        String intentAction = intent.getAction();
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        if (id == 0) {
            Toasty.info(context, "Add random location", Toast.LENGTH_SHORT).show()

            GlobalScope.launch {
                try {
                    addLocationUseCase.build(AddLocationUseCase.Params.random())
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