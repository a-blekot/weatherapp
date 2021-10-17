package com.anadi.weatherapp.view.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.databinding.DetailsFragmentStartBinding
import com.anadi.weatherapp.utils.DateFormats
import com.anadi.weatherapp.utils.Resource
import com.anadi.weatherapp.utils.Status
import com.anadi.weatherapp.view.ui.BaseFragment
import com.google.firebase.database.*
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.*

class DetailsFragment : BaseFragment(R.layout.details_fragment_start) {

    private var binding: DetailsFragmentStartBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var weatherCodes: WeatherCodes

    @Inject
    @Named("Messages")
    lateinit var fbMessages: DatabaseReference

    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            DetailsFragmentStartBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.locationId = DetailsFragmentArgs.fromBundle(requireArguments()).locationId
        viewModel.providerId = DetailsFragmentArgs.fromBundle(requireArguments()).providerId

        adapter = MessageAdapter()
        binding?.messagesRecycler?.apply {
            adapter = this@DetailsFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.detailsNotifier.observe(viewLifecycleOwner, { update(it) })
        binding?.updateButton?.setOnClickListener { viewModel.update() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetch()
        fbMessages.addChildEventListener(childEventListener)
    }

    override fun onPause() {
        super.onPause()
        fbMessages.removeEventListener(childEventListener)
    }

    private fun loading() =
            setProgressVisibility(true)

    private fun error(message: String?) {
        setProgressVisibility(false)
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun update(resource: Resource<LocationWithWeathers>) {
        when (resource.status) {
            Status.SUCCESS -> update(resource.data)
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
    }

    private fun update(data: LocationWithWeathers?) {
        setProgressVisibility(true)

        if (data == null) {
            Timber.d("LocationWithWeathers is null!")
            return
        }

        val location = data.location
        val weather = data.weathers.firstOrNull { it.providerId == viewModel.providerId }

        binding?.apply {
            weatherIcon.setImageResource(weatherCodes.from(weather?.code
                    ?: 0).getIcon(location))
            windIcon.rotation = weather?.windDegree?.toFloat() ?: 0F
            description.text = getString(weatherCodes.from(weather?.code ?: 0).description)
            locationName.text = location.name
            locationAddress.text = location.address
            coordinates.text = location.coord.toString()
            timezone.text = getString(R.string.timezone, location.timeZone.toString())
            temp.text = getString(R.string.temp_celsium, weather?.temp ?: 0)
            tempFeelsLike.text = getString(R.string.temp_feels_like_celsium, weather?.tempFeelsLike
                    ?: 0)
            wind.text = getString(R.string.wind_speed_ms, weather?.windSpeed ?: 0)
            pressure.text = getString(R.string.pressure, weather?.pressure ?: 0)
            humidity.text = getString(R.string.humidity, weather?.humidity ?: 0)
            clouds.text = getString(R.string.clouds, weather?.clouds ?: 0)
            sunrise.text = getString(R.string.sunrise, location.sunrise.toString(DateFormats.sunTime))
            sunset.text = getString(R.string.sunset, location.sunset.toString(DateFormats.sunTime))
        }
    }

    private fun setProgressVisibility(isVisible: Boolean) {
        binding?.progressBar?.root?.isVisible = isVisible
    }

    private val messages = mutableMapOf<String, String>()

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Timber.d("onChildAdded: ${dataSnapshot.key}")
            messages[dataSnapshot.key!!] = dataSnapshot.value.toString()
            Timber.d("onChildAdded: $messages")
            adapter.dataset = messages.map { Message.TextMessage(it.key, it.value) }
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Timber.d("onChildChanged: ${dataSnapshot.key}")
            messages[dataSnapshot.key!!] = dataSnapshot.value.toString()
            Timber.d("onChildChanged: $messages")
            adapter.dataset = messages.map { Message.TextMessage(it.key, it.value) }
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            Timber.d("onChildRemoved: ${dataSnapshot.key}")
            messages.remove(dataSnapshot.key!!)
            Timber.d("onChildRemoved: $messages")
            adapter.dataset = messages.map { Message.TextMessage(it.key, it.value) }
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            Timber.d("onChildChanged: ${dataSnapshot.key}")
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Timber.w(databaseError.toException(), "postComments:onCancelled")
            Toast.makeText(context, "Failed to load comments.", Toast.LENGTH_SHORT).show()
        }
    }
}
