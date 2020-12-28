package com.anadi.weatherinfo.addlocation

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.databinding.ActivityAddLocationBinding
import java.util.*

class AddLocationActivity : AppCompatActivity(R.layout.activity_add_location), OnItemSelectedListener, AddLocationContract.View {

    private val binding by viewBinding(ActivityAddLocationBinding::bind, R.id.layout)

    private lateinit var presenter: AddLocationContract.Presenter
    private var selectedCountry: String? = null
    private var selectedCity: String? = null

    override fun onAddedSuccess() {
        binding.progress.visibility = View.GONE
        finish()
    }

    override fun loading() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun onError(resId: Int) {
        binding.progress.visibility = View.GONE
        Toast.makeText(applicationContext, getText(resId), Toast.LENGTH_LONG).show()
    }

    override fun updateCityList(cities: ArrayList<String?>) {
        binding.citySpinner.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, cities)
    }

    fun addLocation(view: View?) {
        presenter.addLocation(selectedCity, selectedCountry)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (parent.id == R.id.country_spinner) {
            selectedCountry = binding.countrySpinner.getItemAtPosition(position).toString()
            if (!TextUtils.isEmpty(selectedCountry) && !selectedCountry.equals("Select Item", ignoreCase = true)) {
                presenter.onCountrySelected(selectedCountry)
            }
        }
        if (parent.id == R.id.city_spinner) {
            selectedCity = binding.citySpinner.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = AddLocationPresenter(this)

        binding.countrySpinner.setOnItemSelectedListener(this)
        binding.citySpinner.setOnItemSelectedListener(this)
        binding.countrySpinner.setAdapter(presenter.countryNames?.let {
            ArrayAdapter(this, R.layout.spinner_dropdown_item,
                    it)
        })
    }
}