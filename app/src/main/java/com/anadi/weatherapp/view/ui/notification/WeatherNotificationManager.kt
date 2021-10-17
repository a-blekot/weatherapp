package com.anadi.weatherapp.view.ui.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.anadi.weatherapp.R
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import com.anadi.weatherapp.view.work.WeatherUpdateService
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber
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

    fun subscribeToTopics() {
        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnCompleteListener {
            if (!it.isSuccessful) {
                Timber.e(it.exception)
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic("update").addOnCompleteListener {
            if (!it.isSuccessful) {
                Timber.e(it.exception)
            }
        }
    }

    private fun createNotificationChannel() {
//        createNotificationChannels(context) {
//            channel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH) {
//                description = channelDesciption
//                lightsEnabled = true
//                vibrationEnabled = true
//            }
//        }
    }

//    private fun getCustomDesign(title: String, message: String): RemoteViews? {
//        val remoteViews = RemoteViews(context.packageName, R.layout.notification)
//        remoteViews.setTextViewText(R.id.title, title)
//        remoteViews.setTextViewText(R.id.message, message)
//        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_update)
//        return remoteViews
//    }

    fun sendNotification(title: String, body: String, id: Int = 123) {
        if (!areNotificationsEnabled()) {
            return
        }

        val pendingIntent = PendingIntent.getActivity(
                context, 1, MainActivity.getIntent(context), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

//        notification(context, channelId, smallIcon = R.drawable.ic_update) {
//            contentTitle("Weather updated")
//            contentText("Tap to see details")
//            priority(NotificationCompat.PRIORITY_DEFAULT)
//            color(ContextCompat.getColor(context, R.color.colorAccent))
//            autoCancel(true)
//            contentIntent(pendingIntent)
//        }
        val largeIcon = (ResourcesCompat.getDrawable(context.resources, R.drawable.ic_update, null) as VectorDrawable).toBitmap()

        val builder = NotificationCompat.Builder(context, channelId)
//                .setContent(getCustomDesign(title, body))
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSmallIcon(R.drawable.ic_update)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setProgress(0, 0, true)
        //                .addAction(R.drawable.ic_update, "Ok", pendingIntent) // #0

        notificationManager.notify(id, builder.build())
    }

    fun updateNotification() {
        if (!areNotificationsEnabled()) {
            return
        }

        val largeIcon = (ResourcesCompat.getDrawable(context.resources, R.drawable.ic_update, null) as VectorDrawable).toBitmap()

        val pendingIntent = PendingIntent.getService(
                context, 1, WeatherUpdateService.getIntent(context), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action = NotificationCompat.Action(R.drawable.ic_update, "OK", pendingIntent)

        val builder = NotificationCompat.Builder(context, channelId)
//                .setContent(getCustomDesign(title, body))
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSmallIcon(R.drawable.ic_update)
                .setLargeIcon(largeIcon)
                .setContentTitle("Update weather")
                .setContentText("Do you want to update")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(action)
//                .setContentIntent(pendingIntent)
//                .setProgress(0, 0, true)

        notificationManager.notify(444, builder.build())
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

//    fun buildNotifications() {
//        buildNotificationsGroup(context).notify(notificationManager)
//    }
}
