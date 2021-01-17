package com.anadi.weatherinfo.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object DecimalFormats {
    val default = DecimalFormat("#,###", DecimalFormatSymbols(Locale.ENGLISH)).apply {
        isDecimalSeparatorAlwaysShown = false
    }
}
