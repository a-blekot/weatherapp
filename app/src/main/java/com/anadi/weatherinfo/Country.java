package com.anadi.weatherinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Country {
    String name;
    String code;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (null == obj)
            return false;

        if (obj instanceof CityInfo) {
            Country other = (Country) obj;
            return other.name.equals(name) &&
                    other.code.equals(code);
        } else if (obj instanceof String) {
            String string = (String) obj;
            return name.equals(string) ||
                    code.equals(string) ||
                    string.equals(this.toString());
        } else
            return false;
    }

    @NonNull
    @Override
    public String toString() {
        return code + " : " + name;
    }
}
