package com.anadi.weatherapp.data.db

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class Converters {
    @TypeConverter
    fun toDateTime(millis: Long): DateTime {
        return DateTime(millis)
    }

    @TypeConverter
    fun fromDateTime(dateTime: DateTime): Long {
        return dateTime.millis
    }

    @TypeConverter
    fun toDateTimeZone(id: String): DateTimeZone {
        return DateTimeZone.forID(id)
    }

    @TypeConverter
    fun fromDateTimeZone(dateTimeZone: DateTimeZone): String {
        return dateTimeZone.id
    }
}
