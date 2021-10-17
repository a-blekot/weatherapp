package com.anadi.weatherapp.domain.places

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class PlacesWrapperImpl @Inject constructor(val context: Context) : PlacesWrapper {

    override fun createActivityIntent(): Intent {

        return Intent()

//        if (!Places.isInitialized()) {
//            Places.initialize(context, GOOGLE_PLACES_API_KEY)
//        }
//
//        val fields = listOf(
//                Place.Field.ID,
//                Place.Field.NAME,
//                Place.Field.ADDRESS,
//                Place.Field.LAT_LNG,
//                Place.Field.UTC_OFFSET,
//        )
//
//        return Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                .setTypeFilter(TypeFilter.CITIES)
//                .build(context)
    }

    companion object {
        const val GOOGLE_PLACES_API_KEY = "AIzaSyBdtulnVPwSdaGhd2oYRtuUcw-Pjp7RYog"
    }
}
