/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anadi.weatherinfo

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.anadi.weatherinfo.data.db.AppDatabase
import com.anadi.weatherinfo.data.db.BaseDao.Companion.NO_ID
import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var locationDao: LocationDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        locationDao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetLocation() = runBlocking {
        val data = kievLocationEmptyId()
        locationDao.insert(data)

        val location = locationDao.fetch(FIRST_ID)
        assertEquals(location?.id, FIRST_ID)
        assertKievLocation(location)
    }

    @Test
    @Throws(Exception::class)
    fun insertMultipleAndGetLocations() = runBlocking {
        addMultipleKievLocations()

        val data = locationDao.fetch(FIRST_ID)

        assertEquals(data?.id, FIRST_ID)
        assertKievLocation(data)

        val locations = locationDao.fetchAll()
        assertEquals(locations.size, NUMBER_OF_LOCATIONS)

        for (location in locations) {
            assertKievLocation(location)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertMultipleDeleteFewAndGetLocations() = runBlocking {
        addMultipleKievLocations()
        deleteFewKievLocations()

        val location = locationDao.fetch(LAST_ID)

        assertEquals(location?.id, LAST_ID)
        assertKievLocation(location)

        val empty = locationDao.fetch(FIRST_ID)
        assertEquals(empty, null)

        val locations = locationDao.fetchAll()
        assertEquals(locations.size, NUMBER_OF_LOCATIONS - FEW_LOCATIONS)

        for (location in locations) {
            assertKievLocation(location)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertMultipleUpdateAndGetLocations() = runBlocking {
        addMultipleKievLocations()

        var location = locationDao.fetch(FIRST_ID)

        assertEquals(location?.id, FIRST_ID)
        assertKievLocation(location)

        val london = londonLocationWithId(FIRST_ID)
        locationDao.update(london)

        location = locationDao.fetch(FIRST_ID)
        assertEquals(location?.id, FIRST_ID)
        assertLondonLocation(location)

        val locations = locationDao.fetchAll()
        assertEquals(locations.size, NUMBER_OF_LOCATIONS)
    }

    private suspend fun addMultipleKievLocations() {
        val kiev = kievLocationEmptyId()

        for (i in 1..NUMBER_OF_LOCATIONS) {
            locationDao.insert(kiev)
        }
    }

    private suspend fun deleteFewKievLocations() {
        for (i in 1..FEW_LOCATIONS) {
            val location = locationDao.fetch(i)
            locationDao.delete(location!!)
        }
    }

    private fun kievLocationEmptyId() = Location(NO_ID, KIEV_NAME, KIEV_ADDRESS, KIEV_COORD, KIEV_UTC_OFFSET)

    private fun londonLocationEmptyId() = londonLocationWithId(NO_ID)
    private fun londonLocationWithId(id: Int) = Location(id, LONDON_NAME, LONDON_ADDRESS, LONDON_COORD, LONDON_UTC_OFFSET)

    private fun assertKievLocation(location: Location?) {
        assertEquals(location?.name, KIEV_NAME)
        assertEquals(location?.address, KIEV_ADDRESS)
        assertEquals(location?.coord, KIEV_COORD)
        assertEquals(location?.utcOffsetMinutes, KIEV_UTC_OFFSET)
    }

    private fun assertLondonLocation(location: Location?) {
        assertEquals(location?.name, LONDON_NAME)
        assertEquals(location?.address, LONDON_ADDRESS)
        assertEquals(location?.coord, LONDON_COORD)
        assertEquals(location?.utcOffsetMinutes, LONDON_UTC_OFFSET)
    }

    companion object {
        const val NUMBER_OF_LOCATIONS = 5
        const val FEW_LOCATIONS = 2

        const val FIRST_ID = 1
        const val LAST_ID = NUMBER_OF_LOCATIONS

        const val KIEV_NAME = "Kiev"
        const val KIEV_ADDRESS = "Kiev, Ukraine"
        val KIEV_COORD = Coord(50.45466, 30.5238)
        const val KIEV_UTC_OFFSET = 180

        const val LONDON_NAME = "London"
        const val LONDON_ADDRESS = "London, UK"
        val LONDON_COORD = Coord(51.50853, -0.12574)
        const val LONDON_UTC_OFFSET = 0
    }
}


