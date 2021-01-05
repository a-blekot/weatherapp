package com.anadi.weatherinfo.data

data class Country(var name: String, var code: String) {
    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        return if (other is Country) {
            other.name == name && other.code == code
        } else if (other is String) {
            name.equals(other) || code.equals(other)
        } else {
            false
        }
    }

    override fun toString(): String {
        return name
    }

    companion object {
        val EMPTY = Country("", "")
    }
}