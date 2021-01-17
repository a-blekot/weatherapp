package com.anadi.weatherinfo.utils

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

object DateFormats {
    val default: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy").withLocale(Locale.ENGLISH)
    val defaultTime: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.ENGLISH)
}
