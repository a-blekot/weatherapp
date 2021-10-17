package com.anadi.weatherapp

import android.app.Application
import com.anadi.weatherapp.utils.CrashReportingTree
import com.anadi.weatherapp.view.di.Injector
import com.anadi.weatherapp.view.ui.notification.WeatherNotificationManager
import com.anadi.weatherapp.view.work.WorkManagerHelper
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.android.*
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

class WeatherApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)

        WorkManagerHelper.scheduleWork(this)
        Injector.INSTANCE.initialise(this)

        FirebaseApp.initializeApp(this)
//        FirebaseInstallations.getInstance().getToken(false)
//                .addOnCompleteListener {
//                    if (!it.isSuccessful) {
//                        Timber.d("task = $it is not successful")
//                        return@addOnCompleteListener
//                    }
//
//                    val token: String = it.result?.token ?: "EMPTY TOKEN"
//                    Timber.d("token = $token")
//                }
//
//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            if(it.isComplete){
//                val fbToken = it.result.toString()
//                Timber.d("fcmtoken2 = $fbToken")
//            }
//        }

//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
//            val deviceToken = instanceIdResult.token
//            Timber.d("deviceToken = $deviceToken")
//        }

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        notificationManager.subscribeToTopics()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}
