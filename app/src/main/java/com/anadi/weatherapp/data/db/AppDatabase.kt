package com.anadi.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.location.LocationDao
import com.anadi.weatherapp.data.db.user.*
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.db.weather.WeatherDao

@Database(entities = [User::class, Location::class, Weather::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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
