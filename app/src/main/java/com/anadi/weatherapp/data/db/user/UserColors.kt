package com.anadi.weatherapp.data.db.user

import android.os.Parcelable
import androidx.annotation.ColorRes
import com.anadi.weatherapp.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
enum class UserColors(@ColorRes val color: Int) : Parcelable {
    @SerializedName("RED")
    RED(R.color.quantum_googred500),

    @SerializedName("GREEN")
    GREEN(R.color.quantum_googgreen500),

    @SerializedName("BLUE")
    BLUE(R.color.quantum_googblue500),

    @SerializedName("ORANGE")
    ORANGE(R.color.quantum_orange500),

    @SerializedName("PINK")
    PINK(R.color.quantum_pink300)
}