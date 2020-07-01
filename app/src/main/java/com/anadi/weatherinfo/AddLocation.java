package com.anadi.weatherinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class AddLocation extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    Spinner countrySpinner;
    Spinner citySpinner;
    Button addLocationButton;

    String selectedCountry;

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
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.country_spinner) {
            selectedCountry = countrySpinner.getItemAtPosition(position).toString();

            if(!selectedCountry.equalsIgnoreCase("Select Item"))
                setCitySpinner();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCitySpinner() {
        ArrayList<String> cities = Location.getCityNames(selectedCountry);


        if (null == cities) {

            Log.d("anadi_spiner", "EMPTY cities LIST");
            return;
        }
        else
            Log.d("anadi_spiner", cities.toString());

        citySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities ));
    }
}

