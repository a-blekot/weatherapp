package com.anadi.weatherinfo.domain.places

import android.content.Context
import android.content.Intent
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import javax.inject.Inject

class PlacesWrapperImpl @Inject constructor(val context: Context): PlacesWrapper {

    init {
        Places.initialize(context, PLACES_API_KEY)
    }

    override fun createActivityIntent(): Intent {
        val fields = listOf(
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.UTC_OFFSET,)
        return Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.CITIES)
                .build(context)
    }

    companion object {
        const val PLACES_API_KEY = "AIzaSyBdtulnVPwSdaGhd2oYRtuUcw-Pjp7RYog"
    }
}