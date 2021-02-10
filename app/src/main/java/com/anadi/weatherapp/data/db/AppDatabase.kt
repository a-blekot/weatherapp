package com.anadi.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.location.LocationDao
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.db.weather.WeatherDao

@Database(entities = [Location::class, Weather::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    abstract fun weatherDao(): WeatherDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Sample.db"
        ).fallbackToDestructiveMigration().build()
    }
}
