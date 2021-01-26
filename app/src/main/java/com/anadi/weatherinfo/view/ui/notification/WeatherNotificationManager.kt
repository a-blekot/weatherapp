package com.anadi.weatherinfo.view.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.view.ui.mainactivity.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationManager @Inject constructor(private val context: Context) {
    private val notificationManager = NotificationManagerCompat.from(context)

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = context.getString(R.string.channel_name)
            val description = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(context.getString(R.string.channel_id), name, importance)
            channel.description = description
            //            channel.setVibrationPattern();
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor = context.resources.getColor(R.color.colorAccent, null)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(id: Int) {
        createNotificationChannel()

//        val intent = Intent(context, UpdateReceiver::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//            action = UpdateReceiver.NOTIFICATION
//            putExtra(UpdateReceiver.NOTIFICATION_ID, id)
//        }

//        val pendingIntent = PendingIntent.getBroadcast(
//                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
//        )

        val pendingIntent = PendingIntent.getActivity(
                context,
                1,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSmallIcon(R.drawable.ic_update)
                .setContentTitle("Weather updated")
                .setContentText("Tap to see details")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
//                .addAction(R.drawable.ic_update, "Ok", pendingIntent) // #0

        notificationManager.notify(id, builder.build())
    }

    fun showNotification() {

        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))


        val intent = PendingIntent.getActivity(
                context,
                1,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(intent)
        notificationManager.notify(1, builder.build())
    }
}