package com.anadi.weatherinfo.utils

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



//val file: File =
//    measureTimeMillis({ time -> Log.d(TAG, "Read and decode took $time") }) {
//        readAndDecodeFile()
//    }