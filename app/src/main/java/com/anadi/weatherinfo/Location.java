package com.anadi.weatherinfo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Location {

    //private static Map<Country, ArrayList<String>> locations = new HashMap<>();

    private static Context context;
    private static ArrayList<Country> countries = new ArrayList<>();
    private static Map<String, ArrayList<String>> cities = new HashMap<>();

    public static void setContext(Context c) {
        context = c;
    }

    public static void loadLocations() {
        String jsonString = convert(context.getResources().openRawResource(R.raw.countries_codes));
//        Log.d("anadi_location", jsonString);

        try {
//            Log.d("anadi_location", "I`.m here!");
            JSONArray array = new JSONArray(jsonString);
            JSONObject obj;
            for (int i = 0; i < array.length(); i++) {
                obj = array.getJSONObject(i);
                countries.add(new Country(obj.getString("name"), obj.getString("code")));
            }

//            Log.d("anadi_location", countries.toString());
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
                    Log.d("anadi_location", "There are no cities for country: " + country.name);
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

    private static String convert(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
//                Log.d("anadi_json", line);
            }
        }
        catch (IOException e) {
            Log.d("anadi_json", e.getMessage());
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static ArrayList<String> getCityNames(String country) {

        Log.d("anadi_cities", "get cities for: " + country);

        return cities.get(country);
    }

    public static ArrayList<String> getCountryNamesArray() {

        ArrayList<String> countryNames = new ArrayList<>();

        for (Country c: countries)
            countryNames.add(c.toString());

        return countryNames;
    }
}
