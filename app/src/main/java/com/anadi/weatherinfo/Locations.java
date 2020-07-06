package com.anadi.weatherinfo;

import android.content.Context;

import com.anadi.weatherinfo.addlocation.LocationsProvider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import timber.log.Timber;

public class Locations implements LocationsProvider {

    private static Locations instance = new Locations();

    private Random random = new Random(System.currentTimeMillis());

    private Context context;
    private ArrayList<Country> countries = new ArrayList<>();
    private Map<String, ArrayList<String>> cities = new HashMap<>();

    private Locations() {}
    public static Locations getInstatnce() { return instance; }

    public void setContext(Context c) {
        context = c;
    }

    public void loadLocations() {
        String jsonString = convert(context.getResources().openRawResource(R.raw.countries_codes));
//        Timber.d( jsonString);

        try {
//            Timber.d( "I`.m here!");
            JSONArray array = new JSONArray(jsonString);
            JSONObject obj;
            for (int i = 0; i < array.length(); i++) {
                obj = array.getJSONObject(i);
                countries.add(new Country(obj.getString("name"), obj.getString("code")));
            }

//            Timber.d( countries.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            for (Country country: countries) {
                String resourceName = country.name.toLowerCase().replace(" ","_");

                jsonString = convert(context.getResources().openRawResource(
                        context.getResources().getIdentifier(
                                resourceName, "raw", context.getPackageName())));

                JSONObject obj = new JSONObject(jsonString);
                JSONArray array = obj.getJSONArray(country.name);

                if (null == array) {
//                    countries.remove(c);
                    Timber.d( "There are no cities for country: " + country.name);
                    continue;
                }

                ArrayList<String> cityArray = new ArrayList<>();
                for (int i = 0; i < array.length(); i++)
                    cityArray.add(array.getString(i));

                cities.put(country.toString(), cityArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String convert(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
//                Timber.d( line);
            }
        }
        catch (IOException e) {
            Timber.d( e.getMessage());
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public ArrayList<String> getCityNames(String country) {

//        Timber.d( "get cities for: " + country);

        return cities.get(country);
    }

    public ArrayList<String> getCountryNames() {

        ArrayList<String> countryNames = new ArrayList<>();

        for (Country c: countries)
            countryNames.add(c.toString());

        return countryNames;
    }

    public Country getCountryByName(String countryName) {

        Iterator<Country> it = countries.listIterator();
        while (it.hasNext()) {
            Country country = it.next();
//            if (country.equals(countryName))
            if (country.name.equals(countryName))
                return country;
        }

        return null;

//        int index = countries.indexOf(countryName);
//        if (index == -1)
//            return null;
//
//        return countries.get(index);
    }

    public Country getRandomCountry() {
        if (countries.isEmpty())
            return null;

        return countries.get(random.nextInt(countries.size()));
    }

    public String getRandomCity(String countryName) {
        ArrayList<String> cityNames = getCityNames(countryName);
        if (null == cityNames ||
                cityNames.isEmpty())
            return null;

        return cityNames.get(random.nextInt(cityNames.size()));
    }
}
