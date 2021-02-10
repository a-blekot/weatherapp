package com.anadi.weatherapp.view.work

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.anadi.weatherapp.R
import com.anadi.weatherapp.view.ui.mainactivity.MainActivity
import timber.log.Timber
import javax.inject.Inject

class MyService @Inject constructor(val context: Context) : Service() {
    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("My foreground service onCreate().")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
            when (intent.action) {
                ACTION_START_FOREGROUND_SERVICE -> {
                    startForegroundService()
                    Toast.makeText(applicationContext, "Foreground service is started.", Toast.LENGTH_LONG).show()
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    stopForegroundService()
                    Toast.makeText(applicationContext, "Foreground service is stopped.", Toast.LENGTH_LONG).show()
                }
                ACTION_PLAY -> Toast.makeText(applicationContext, "You click Play button.", Toast.LENGTH_LONG).show()
                ACTION_PAUSE -> Toast.makeText(applicationContext, "You click Pause button.", Toast.LENGTH_LONG).show()
            }

        return super.onStartCommand(intent, flags, startId)
    }

    /* Used to build and start foreground service. */
    private fun startForegroundService() {
        Timber.d( "Start foreground service.")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel("my_service", "My Background Service")
//        }
//
//        else {

            // Create notification default intent.
            val intent = Intent()
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            // Create notification builder.
            val builder = NotificationCompat.Builder(context)

            // Make notification show big text.
            val bigTextStyle: NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.setBigContentTitle("Music player implemented by foreground service.")
            bigTextStyle.bigText("Android foreground service is a android service which can run in foreground always, it can be controlled by user via notification.")
            // Set big text style.
            builder.setStyle(bigTextStyle)
            builder.setWhen(System.currentTimeMillis())
            builder.setSmallIcon(R.mipmap.ic_launcher)
            val largeIconBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background)
            builder.setLargeIcon(largeIconBitmap)
            // Make the notification max priority.
            builder.priority = NotificationCompat.PRIORITY_HIGH
            // Make head-up notification.
            builder.setFullScreenIntent(pendingIntent, true)

            // Add Play button intent in notification.
            val playIntent = Intent(this, MyService::class.java)
            playIntent.action = ACTION_PLAY
            val pendingPlayIntent: PendingIntent = PendingIntent.getService(this, 0, playIntent, 0)
            val playAction: NotificationCompat.Action = NotificationCompat.Action(R.drawable.ic_media_play, "Play", pendingPlayIntent)
            builder.addAction(playAction)

            // Add Pause button intent in notification.
            val pauseIntent = Intent(this, MyService::class.java)
            pauseIntent.action = ACTION_PAUSE
            val pendingPrevIntent: PendingIntent = PendingIntent.getService(this, 0, pauseIntent, 0)
            val prevAction: NotificationCompat.Action = NotificationCompat.Action(R.drawable.ic_media_pause, "Pause", pendingPrevIntent)
            builder.addAction(prevAction)

            // Start foreground service.
            startForeground(1, builder.build())
//        }
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(channelId: String, channelName: String) {
//        val resultIntent = MainActivity.getIntent(this)
//        // Create the TaskStackBuilder and add the intent, which inflates the back stack
//        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
//        stackBuilder.addNextIntentWithParentStack(resultIntent)
//        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
////        val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
////        chan.setLightColor(Color.BLUE)
////        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE)
////
////        val manager: NotificationManager = (getSystemService<Any>(Context.NOTIFICATION_SERVICE) as NotificationManager)!!
////        manager.createNotificationChannel(chan)
//
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//        val notification = notificationBuilder.setOngoing(true)
//                .setSmallIcon(R.drawable.ic_up)
//                .setContentTitle("App is running in background")
//                .setPriority(NotificationManager.IMPORTANCE_MIN)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setContentIntent(resultPendingIntent) //intent
//                .build()
//        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(this)
//        notificationManager.notify(1, notificationBuilder.build())
//        startForeground(1, notification)
//    }

    private fun stopForegroundService() {
        Timber.d( "Stop foreground service.")

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }

    companion object {
        const val ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE"
        const val ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_PLAY = "ACTION_PLAY"
    }
}

//Usage
//Intent intent = new Intent(MainActivity.this, MyService.class);
//intent.setAction(MyService.ACTION_STOP_FOREGROUND_SERVICE);
//startService(intent);
//
//Intent intent = new Intent(MainActivity.this, MyService.class);
//intent.setAction(MyService.ACTION_START_FOREGROUND_SERVICE);
//startService(intent);