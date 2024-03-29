package com.anadi.weatherapp.view.ui.notification

import com.anadi.weatherapp.view.di.Injector
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class WeatherMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    override fun onCreate() {
        super.onCreate()
        Injector.INSTANCE.applicationComponent.inject(this)
    }

//    Content-Type -> application/json
//    Authorization -> key=AAAAhX8ajcA:APA91bGuma5XVB0MgPO7ihwxGmHZ8KhvtR1gYvnCUBahBU0X3vC9s8Mu8z__S2KjQtlPeQUmKf32NSvAahuS-4D-A_KY7xMD5hJJEHnszDmm5CYgQU8lepauErWbNc_Y9yw0Ybn_rQyD
//    {
//        "to":"/topics/general",
//        "notification":{
//        "title":"Test notification",
//        "body":"Nice weather and cute clouds)"
//    }
//    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val type = message.from
        Timber.d("FCM type = $type")
        val title = message.notification?.title ?: "Title"
        val body = message.notification?.body ?: "Body"
        when (type) {
            "/topics/general" -> notificationManager.sendNotification(title, body)
            "/topics/update" -> notificationManager.updateNotification()
        }

        notificationManager.sendNotification(title, body)
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }
}