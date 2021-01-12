package com.anadi.weatherinfo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class UpdateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        //        int id = (int) getInputData().getLong("notification", 0);
        createNotificationChannel()
        sendNotification(0)
        return Result.success()
    }

    private fun sendNotification(id: Int) {
        val intent = Intent(applicationContext, UpdateReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(UpdateReceiver.NOTIFICATION_ID, id)
        intent.action = UpdateReceiver.NOTIFICATION

        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).setSmallIcon(R.drawable.ic_update)
                .setContentTitle("Weather Info")
                .setContentText("Do you  want to update info?")
                .addAction(R.drawable.ic_update, "Ok", pendingIntent) // #0
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(applicationContext).notify(id, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = applicationContext.getString(R.string.channel_name)
            val description = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            //            channel.setVibrationPattern();
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.lightColor = applicationContext.resources.getColor(R.color.colorAccent, null)
            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "com.anadi.weatherinfo.UpdateChannel"
    }
}
