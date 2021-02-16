package com.anadi.weatherapp.view.ui.details

import com.anadi.weatherapp.data.db.user.User

sealed class Message {
    data class TextMessage(val author: String, val text: String)
}