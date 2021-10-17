package com.anadi.weatherapp.data.db.user

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorRes
import com.anadi.weatherapp.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
enum class UserColors(val color: Int) : Parcelable {
    @SerializedName("RED")
    RED(Color.RED),

    @SerializedName("GREEN")
    GREEN(Color.GREEN),

    @SerializedName("BLUE")
    BLUE(Color.BLUE),

    @SerializedName("ORANGE")
    ORANGE(Color.rgb(255, 69, 0)),

    @SerializedName("PINK")
    PINK(Color.rgb(255, 192, 203))
}