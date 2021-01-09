package com.anadi.weatherinfo.view.ui.addlocation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.databinding.AddLocationFragmentBinding
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.Status
import com.anadi.weatherinfo.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import javax.inject.Inject

class AddLocationFragment : BaseFragment(R.layout.add_location_fragment), OnItemSelectedListener {

    private val binding: AddLocationFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: AddLocationViewModel

    private var selectedCountry = ""
    private var selectedCity = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddLocationViewModel::class.java)

        viewModel.citiesNotifier.observe(viewLifecycleOwner, Observer { updateCities(it) })
        viewModel.statusNotifier.observe(viewLifecycleOwner, Observer { updateStatus(it) })

        binding.confirmButton.setOnClickListener { viewModel.addLocation(selectedCity, selectedCountry) }

        binding.countrySpinner.onItemSelectedListener = this
        binding.citySpinner.onItemSelectedListener = this
    }

    private fun updateCities(cities: List<String>) {
        binding.citySpinner.adapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, cities)
    }

    private fun updateStatus(resource: Resource<Any>) {
        when (resource.status) {
            Status.SUCCESS -> success(resource.message(requireContext()))
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
    }

    private fun success(message: String?) {
        hideProgress()
        message?.let { Toasty.success(requireContext(), it, Toast.LENGTH_LONG).show() }
        findNavController().popBackStack()
    }

    private fun loading() {
        showProgress()
    }

    private fun error(message: String?) {
        hideProgress()
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun hideProgress() {
        binding.progressBar.root.visibility = View.GONE
    }

    private fun showProgress() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (parent.id == R.id.city_spinner) {
            selectedCity = binding.citySpinner.getItemAtPosition(position).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}