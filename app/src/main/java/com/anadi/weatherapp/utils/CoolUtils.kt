package com.anadi.weatherapp.utils

import android.R
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import kotlin.system.measureTimeMillis

inline fun <T> measureTimeMillis(logFunction: (Long) -> Unit,
                                 function: () -> T): T {
    var result: T
    val time = measureTimeMillis {
        result = function.invoke()
    }

    logFunction.invoke(time)
    return result
}

class CrashReportingTree : Timber.Tree() {

    private val CRASHLYTICS_KEY_PRIORITY = "priority"
    private val CRASHLYTICS_KEY_TAG = "tag"
    private val CRASHLYTICS_KEY_MESSAGE = "message"

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        val t: Throwable = throwable ?: Exception(message)

        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_PRIORITY, R.attr.priority)
        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_TAG, tag ?: "")
        FirebaseCrashlytics.getInstance().setCustomKey(CRASHLYTICS_KEY_MESSAGE, message)
        FirebaseCrashlytics.getInstance().recordException(t)
    }
}


//val file: File =
//    measureTimeMillis({ time -> Log.d(TAG, "Read and decode took $time") }) {
//        readAndDecodeFile()
//    }