package com.anadi.weatherapp.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

object DateFormats {
    val default: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy").withLocale(Locale.ENGLISH)
    val defaultTime: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.ENGLISH)
    val sunTime: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss").withLocale(Locale.ENGLISH)
    val sunDatetime: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ").withLocale(Locale.ENGLISH)
}
