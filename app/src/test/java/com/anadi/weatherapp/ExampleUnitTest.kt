package com.anadi.weatherapp

import com.anadi.weatherapp.data.db.location.Coord.Companion.PI_RADIAN
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.math.abs

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(Parameterized::class)
class ExampleUnitTest(val lon: Double) {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(5, 2 + 3.toLong())
    }

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun input() = listOf(-270.0, -230.0, -200.0, -190.0, 270.0, 240.0, 220.0, 190.0)
    }

    @Test
    fun longitudeInRange() {
        val checkedLon1 = checkLon1(lon)
        val checkedLon2 = checkLon2(lon)

        val THRESHOLD = .0001

        assert(abs(checkedLon1 - checkedLon1) < THRESHOLD) {
            "abs($checkedLon1 - $checkedLon2) < $THRESHOLD"
        }
    }

    private fun checkLon1(lon: Double): Double {
        @Suppress("LiftReturnOrAssignment")
        if (lon in -PI_RADIAN..PI_RADIAN) {
            return lon
        } else {
            return ((lon - PI_RADIAN) % 360.0 + 360.0) % 360.0 - PI_RADIAN
        }
    }

    private fun checkLon2(lon: Double): Double {
        @Suppress("LiftReturnOrAssignment")
        if (lon in -PI_RADIAN..PI_RADIAN) {
            return lon
        } else {
            return (lon - PI_RADIAN) % 360.0 - PI_RADIAN
        }
    }
}
