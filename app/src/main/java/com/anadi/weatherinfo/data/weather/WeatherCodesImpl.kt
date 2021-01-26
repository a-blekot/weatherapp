package com.anadi.weatherinfo.data.weather

import android.content.Context
import com.anadi.weatherinfo.R
import javax.inject.Inject
import kotlin.math.abs

@Suppress("MagicNumber")
private const val MIN_CODE = 200
@Suppress("MagicNumber")
private const val MAX_CODE = 804

@Suppress("MagicNumber")
class WeatherCodesImpl @Inject constructor(private val context: Context) : WeatherCodes {

    private val allCodes: List<WeatherCode> = listOf(
            // THUNDERSTORM
            WeatherCode(200, R.string.description_200, icon("t01d"), icon("t01n")),
            WeatherCode(201, R.string.description_201, icon("t02d"), icon("t02n")),
            WeatherCode(202, R.string.description_202, icon("t03d"), icon("t03n")),
            WeatherCode(210, R.string.description_210, icon("t04d"), icon("t04n")),
            WeatherCode(211, R.string.description_211, icon("t04d"), icon("t04n")),
            WeatherCode(212, R.string.description_212, icon("t05d"), icon("t05n")),
            WeatherCode(221, R.string.description_211, icon("t04d"), icon("t04n")),
            WeatherCode(230, R.string.description_230, icon("t04d"), icon("t04n")),
            WeatherCode(231, R.string.description_231, icon("t04d"), icon("t04n")),
            WeatherCode(232, R.string.description_232, icon("t04d"), icon("t04n")),
            WeatherCode(233, R.string.description_233, icon("t05d"), icon("t05n")),

            // DRIZZLE
            WeatherCode(300, R.string.description_300, icon("d01d"), icon("d01n")),
            WeatherCode(301, R.string.description_301, icon("d02d"), icon("d02n")),
            WeatherCode(302, R.string.description_302, icon("d03d"), icon("d03n")),
            WeatherCode(310, R.string.description_300, icon("d02d"), icon("d02n")),
            WeatherCode(311, R.string.description_301, icon("d02d"), icon("d02n")),
            WeatherCode(312, R.string.description_302, icon("d02d"), icon("d02n")),
            WeatherCode(313, R.string.description_521, icon("d02d"), icon("d02n")),
            WeatherCode(314, R.string.description_522, icon("d02d"), icon("d02n")),
            WeatherCode(321, R.string.description_302, icon("d02d"), icon("d02n")),

            // RAIN
            WeatherCode(500, R.string.description_500, icon("r01d"), icon("r01n")),
            WeatherCode(501, R.string.description_501, icon("r02d"), icon("r02n")),
            WeatherCode(502, R.string.description_502, icon("r03d"), icon("r03n")),
            WeatherCode(503, R.string.description_503, icon("r04d"), icon("r04n")),
            WeatherCode(504, R.string.description_504, icon("r04d"), icon("r04n")),
            WeatherCode(511, R.string.description_511, icon("f01d"), icon("f01n")),
            WeatherCode(520, R.string.description_520, icon("r04d"), icon("r04n")),
            WeatherCode(521, R.string.description_521, icon("r05d"), icon("r05n")),
            WeatherCode(522, R.string.description_522, icon("r06d"), icon("r06n")),
            WeatherCode(531, R.string.description_531, icon("r06d"), icon("r06n")),
            WeatherCode(532, R.string.description_532, icon("r04d"), icon("r04n")),

            // SNOW
            WeatherCode(600, R.string.description_600, icon("s01d"), icon("s01n")),
            WeatherCode(601, R.string.description_601, icon("s02d"), icon("s02n")),
            WeatherCode(602, R.string.description_602, icon("s03d"), icon("s03n")),
            WeatherCode(610, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(611, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(612, R.string.description_612, icon("s05d"), icon("s05n")),
            WeatherCode(613, R.string.description_612, icon("s05d"), icon("s05n")),
            WeatherCode(615, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(616, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(620, R.string.description_621, icon("s01d"), icon("s01n")),
            WeatherCode(621, R.string.description_621, icon("s01d"), icon("s01n")),
            WeatherCode(622, R.string.description_622, icon("s02d"), icon("s02n")),
            WeatherCode(623, R.string.description_623, icon("s06d"), icon("s06n")),

            // ATMOSPHERE
            WeatherCode(700, R.string.description_700, icon("a01d"), icon("a01n")),
            WeatherCode(711, R.string.description_711, icon("a02d"), icon("a02n")),
            WeatherCode(721, R.string.description_721, icon("a03d"), icon("a03n")),
            WeatherCode(731, R.string.description_731, icon("a04d"), icon("a04n")),
            WeatherCode(741, R.string.description_741, icon("a05d"), icon("a05n")),
            WeatherCode(751, R.string.description_751, icon("a06d"), icon("a06n")),
            WeatherCode(761, R.string.description_761, icon("a04d"), icon("a04d")),
            WeatherCode(762, R.string.description_762, icon("a04d"), icon("a04d")),
            WeatherCode(771, R.string.description_771, icon("s05d"), icon("s05n")),
            WeatherCode(781, R.string.description_781, icon("s05d"), icon("s05n")),

            // CLOUDS
            WeatherCode(800, R.string.description_800, icon("c01d"), icon("c01n")),
            WeatherCode(801, R.string.description_801, icon("c02d"), icon("c02n")),
            WeatherCode(802, R.string.description_802, icon("c02d"), icon("c02n")),
            WeatherCode(803, R.string.description_803, icon("c03d"), icon("c03n")),
            WeatherCode(804, R.string.description_804, icon("c04d"), icon("c04n")),

            WeatherCode(900, R.string.description_900, icon("u00d"), icon("u00n")),

            // WEATHERAPI.COM codes
            WeatherCode(1000, R.string.description_800, icon("c01d"), icon("c01n")),
            WeatherCode(1003, R.string.description_801, icon("c02d"), icon("c02n")),
            WeatherCode(1006, R.string.description_802, icon("c02d"), icon("c02n")),
            WeatherCode(1009, R.string.description_804, icon("c04d"), icon("c04n")),
            WeatherCode(1030, R.string.description_700, icon("a01d"), icon("a01n")),
            WeatherCode(1063, R.string.description_500, icon("a01d"), icon("a01n")),
            WeatherCode(1066, R.string.description_600, icon("a01d"), icon("a01n")),
            WeatherCode(1069, R.string.description_610, icon("a01d"), icon("a01n")),
            WeatherCode(1072, R.string.description_300, icon("a01d"), icon("a01n")),
            WeatherCode(1087, R.string.description_210, icon("a01d"), icon("a01n")),
            WeatherCode(1114, R.string.description_1114, icon("s05d"), icon("s05n")),
            WeatherCode(1114, R.string.description_1117, icon("s05d"), icon("s05n")),
            WeatherCode(1135, R.string.description_741, icon("a05d"), icon("a05n")),
            WeatherCode(1147, R.string.description_751, icon("a06d"), icon("a06n")),
            WeatherCode(1150, R.string.description_300, icon("d01d"), icon("d01n")),
            WeatherCode(1153, R.string.description_300, icon("d01d"), icon("d01n")),
            WeatherCode(1168, R.string.description_301, icon("d02d"), icon("d02n")),
            WeatherCode(1171, R.string.description_302, icon("d03d"), icon("d03n")),
            WeatherCode(1180, R.string.description_500, icon("r01d"), icon("r01n")),
            WeatherCode(1183, R.string.description_500, icon("r01d"), icon("r01n")),
            WeatherCode(1186, R.string.description_501, icon("r02d"), icon("r02n")),
            WeatherCode(1189, R.string.description_501, icon("r02d"), icon("r02n")),
            WeatherCode(1192, R.string.description_502, icon("r03d"), icon("r03n")),
            WeatherCode(1195, R.string.description_502, icon("r03d"), icon("r03n")),
            WeatherCode(1198, R.string.description_511, icon("f01d"), icon("f01n")),
            WeatherCode(1201, R.string.description_511, icon("f01d"), icon("f01n")),
            WeatherCode(1204, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(1207, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(1210, R.string.description_600, icon("s01d"), icon("s01n")),
            WeatherCode(1213, R.string.description_600, icon("s01d"), icon("s01n")),
            WeatherCode(1216, R.string.description_601, icon("s02d"), icon("s02n")),
            WeatherCode(1219, R.string.description_601, icon("s02d"), icon("s02n")),
            WeatherCode(1222, R.string.description_602, icon("s03d"), icon("s03n")),
            WeatherCode(1225, R.string.description_602, icon("s03d"), icon("s03n")),
            WeatherCode(1237, R.string.description_532, icon("r04d"), icon("r04n")),
            WeatherCode(1240, R.string.description_520, icon("r04d"), icon("r04n")),
            WeatherCode(1243, R.string.description_521, icon("r04d"), icon("r04n")),
            WeatherCode(1246, R.string.description_522, icon("r06d"), icon("r06n")),
            WeatherCode(1249, R.string.description_610, icon("s04d"), icon("s04n")),
            WeatherCode(1252, R.string.description_612, icon("s05d"), icon("s05n")),
            WeatherCode(1255, R.string.description_621, icon("s01d"), icon("s01n")),
            WeatherCode(1258, R.string.description_622, icon("s02d"), icon("s02n")),
            WeatherCode(1261, R.string.description_532, icon("r04d"), icon("r04n")),
            WeatherCode(1264, R.string.description_532, icon("r04d"), icon("r04n")),
            WeatherCode(1273, R.string.description_200, icon("t01d"), icon("t01n")),
            WeatherCode(1276, R.string.description_201, icon("t02d"), icon("t02n")),
            WeatherCode(1279, R.string.description_200, icon("t01d"), icon("t01n")),
            WeatherCode(1282, R.string.description_201, icon("t02d"), icon("t02n"))
    )

    private val unknown = allCodes.last()

    private fun icon(name: String) = context.resources.getIdentifier(name, "drawable", context.packageName)

    override fun from(code: Int): WeatherCode {
        return allCodes.firstOrNull { it.code == code } ?:
        when {
            code < MIN_CODE -> unknown
            code > MAX_CODE -> unknown
            else -> return find(code)
        }
    }

    private fun find(code: Int): WeatherCode {
        var diff = Int.MAX_VALUE
        var result = allCodes.first()

        for (weatherCode in allCodes) {
            val currentDiff = abs(code - weatherCode.code)
            if (diff > currentDiff) {
                diff = currentDiff
                result = weatherCode
            } else {
                break
            }
        }
        return result
    }

//    private fun binaryFind(code: Int): WeatherCode {
//        var leftIndex = 0
//        var rightIndex = allCodes.size - 1
//
//        var iterations = 0
//
//        while (leftIndex != rightIndex) {
//            val leftDiff = abs(code - allCodes[leftIndex].code)
//            val rightDiff = abs(code - allCodes[rightIndex].code)
//
//            val newIndex = (leftIndex + rightIndex + 1) / 2
//            if (leftDiff <= rightDiff) {
//                if (rightIndex == newIndex) {
//                    break
//                }
//                rightIndex = newIndex
//            } else {
//                if (leftIndex == newIndex) {
//                    leftIndex = rightIndex
//                    break
//                }
//                leftIndex = newIndex
//            }
//
//            if (iterations++ > allCodes.size / 2) {
//                throw IllegalStateException("binaryFind(code = $code). while loop takes to many iterations..." +
//                                                    "leftIndex = $leftIndex, rightIndex = $rightIndex")
//            }
//        }
//        return allCodes[leftIndex]
//    }
}
