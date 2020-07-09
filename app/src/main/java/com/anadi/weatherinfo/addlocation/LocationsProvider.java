package com.anadi.weatherinfo.addlocation;

import android.content.Context;

import com.anadi.weatherinfo.repository.Country;

import java.util.ArrayList;

public interface LocationsProvider {
    void setContext(Context context);
    void loadLocations();

    ArrayList<String> getCityNames(String country);
    ArrayList<String> getCountryNames();

    Country getCountryByName(String countryName);

    Country getRandomCountry();
    String getRandomCity(String countryName);
}
