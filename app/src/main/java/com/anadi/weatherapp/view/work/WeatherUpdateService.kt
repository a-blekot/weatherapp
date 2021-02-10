package com.anadi.weatherapp.view.work

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.anadi.weatherapp.R
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import timber.log.Timber


class WeatherUpdateService : Service() {

    companion object {
        fun getIntent(context: Context) = Intent(context, WeatherUpdateService::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand service on thread: ${Thread.currentThread().name}")

        startUpdate()

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind service on thread: ${Thread.currentThread().name}")
        return null
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Timber.d("onRebind service on thread: ${Thread.currentThread().name}")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.d("onUnbind service on thread: ${Thread.currentThread().name}")
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate service on thread: ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy service on thread: ${Thread.currentThread().name}")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Timber.d("onLowMemory service on thread: ${Thread.currentThread().name}")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Timber.d("onTaskRemoved service on thread: ${Thread.currentThread().name}")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Timber.d("onTrimMemory service on thread: ${Thread.currentThread().name}")
    }

    private fun startUpdate() {
        val pendingIntent = PendingIntent.getActivity(
                this, 1, MainActivity.getIntent(this), PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setContentTitle("Foreground Service")
                .setContentText("input")
                .setSmallIcon(R.drawable.ic_update)
                .setContentIntent(pendingIntent)
                .build()

        notification.flags = notification.flags or Notification.FLAG_NO_CLEAR

        startForeground(333, notification)
    }

    private fun stopUpdate() {
        stopForeground(true)
        stopSelf()
    }
}