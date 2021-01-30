package com.anadi.weatherinfo.view.ui.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.view.ui.mainactivity.MainActivity
import com.kirich1409.androidnotificationdsl.channels.createNotificationChannels
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationManager @Inject constructor(private val context: Context) {
    private val notificationManager = NotificationManagerCompat.from(context)

    private val channelId = context.getString(R.string.channel_id)
    private val channelName = context.getString(R.string.channel_name)
    private val channelDesciption = context.getString(R.string.channel_description)

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        createNotificationChannels(context) {
            channel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH) {
                description = channelDesciption
                lightsEnabled = true
                vibrationEnabled = true
            }
        }
    }

    fun sendNotification(id: Int) {
        if (!areNotificationsEnabled()) {
            return
        }
        val pendingIntent = PendingIntent.getActivity(
                context, 1, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )

//        notification(context, channelId, smallIcon = R.drawable.ic_update) {
//            contentTitle("Weather updated")
//            contentText("Tap to see details")
//            priority(NotificationCompat.PRIORITY_DEFAULT)
//            color(ContextCompat.getColor(context, R.color.colorAccent))
//            autoCancel(true)
//            contentIntent(pendingIntent)
//        }

        val builder = NotificationCompat.Builder(context, channelId)
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

    private fun areNotificationsEnabled(): Boolean {
        return if (!notificationManager.areNotificationsEnabled()) {
            false
        } else {
            notificationChannelEnabled()
        }
    }

    @Suppress("ReturnCount")
    private fun notificationChannelEnabled(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(channelId)
            if (channel == null || channel.importance == NotificationManagerCompat.IMPORTANCE_NONE) {
                return false
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val channelGroup = channel.group?.let { notificationManager.getNotificationChannelGroup(it) }
                if (channelGroup != null && channelGroup.isBlocked) {
                    return false
                }
            }
        }
        return true
    }

    fun buildNotifications() {
        buildNotificationsGroup(context).notify(notificationManager)
    }
}
