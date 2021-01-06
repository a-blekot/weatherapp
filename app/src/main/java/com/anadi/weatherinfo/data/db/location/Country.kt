package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo

data class Country(
        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "code")
        val code: String) {

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }

        return when (other) {
            is Country -> other.name == name && other.code == code
            is String -> name == other || code == other
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + code.hashCode()
        return result
    }

    override fun toString(): String {
        return name
    }

    companion object {
        val EMPTY = Country("", "")
    }
}