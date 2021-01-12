package com.anadi.weatherinfo.utils

import android.content.Context
import androidx.annotation.StringRes

data class Resource<out T>(val status: Status, val data: T?, @StringRes val messageId: Int?, val message: String?) {

    fun message(context: Context): String? {
        messageId?.let { return context.getString(messageId) }
        return message
    }

    companion object {
        fun <T> success(@StringRes messageId: Int? = null, data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, messageId, null)
        }

        fun <T> error(@StringRes messageId: Int, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, messageId, null)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}
