package com.anadi.weatherinfo.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.*;

public class IconMap {

    private static boolean inited = false;
    private static Map<String, Integer> icons = new HashMap<>();
    private static String[] iconNames = {
            "s01d", "s02d", "s03d", "s04d", "s09d",
            "s10d", "s11d", "s13d", "s50d",
            "s01n", "s02n", "s03n", "s04n", "s09n",
            "s10n", "s11n", "s13n", "s50n"};
//            "01d", "02d", "03d", "04d", "09d",
//            "10d", "11d", "13d", "50d",
//            "01n", "02n", "03n", "04n", "09n",
//            "10n", "11n", "13n", "50n"};

    public static void init(Context context) {
        if (inited)
            return;

        int id;
        for (String iconName: iconNames) {
            id = context.getResources().getIdentifier(
                    iconName,
                    "drawable",
                    context.getPackageName());
            icons.put(iconName, id);
        }
        inited = true;
    }

    // choose icon with the same name as info.weather.icon
    public static int getIconId(@NonNull String iconName) {
        return icons.get("s" + iconName);
    }
}
