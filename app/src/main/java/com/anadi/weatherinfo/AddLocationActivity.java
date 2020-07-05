package com.anadi.weatherinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import timber.log.Timber;

public class AddLocationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    Spinner countrySpinner;
    Spinner citySpinner;
    Button addLocationButton;

    private String selectedCountry;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        ArrayList<String> countries = new ArrayList<String>();

        countries = Location.getCountryNamesArray();


        countrySpinner = findViewById(R.id.country_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        addLocationButton = findViewById(R.id.add_location_button);

        countrySpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);

        countrySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, countries ));
    }


    public void addLocation(View view) {

        if (TextUtils.isEmpty(selectedCity) || TextUtils.isEmpty(selectedCountry) ||
                selectedCity.equalsIgnoreCase("Select Item") ||
                selectedCountry.equalsIgnoreCase("Select Item")) {
            Timber.d( "selectedCity: " + selectedCity +
                                           "selectedCountry: " + selectedCountry);
            return;
        }

        if (!CitiesCash.add(selectedCity, selectedCountry)) {
            Toast.makeText(getApplicationContext(),
                    "Can`t load info for:" + selectedCity + " (selectedCountry).",
                    Toast.LENGTH_LONG).show();
        }

        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.country_spinner) {
            selectedCountry = countrySpinner.getItemAtPosition(position).toString();

            if(!selectedCountry.equalsIgnoreCase("Select Item"))
                setCitySpinner();
        }

        if (parent.getId() == R.id.city_spinner)
            selectedCity = citySpinner.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCitySpinner() {
        ArrayList<String> cities = Location.getCityNames(selectedCountry);


        if (null == cities) {

            Timber.d( "EMPTY cities LIST");
            return;
        }
        else
            Timber.d( cities.toString());

        citySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities ));
    }
}

