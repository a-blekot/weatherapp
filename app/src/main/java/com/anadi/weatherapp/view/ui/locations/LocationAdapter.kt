package com.anadi.weatherapp.view.ui.locations

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.data.weather.WeatherCode
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.databinding.LocationRowViewBinding
import com.anadi.weatherapp.view.ui.locations.LocationAdapter.LocationHolder

class LocationAdapter(
        private val listener: Listener,
        private val weatherCodes: WeatherCodes
) : RecyclerView.Adapter<LocationHolder>() {
    interface Listener {
        fun onSelected(location: Location)
        fun onMenuAction(location: Location, item: MenuItem)
    }

    var dataset = emptyList<LocationWithWeathers>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {

        val binding = LocationRowViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocationHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val weatherCode = weatherCodes.from(dataset[position].weathers.getOrNull(0)?.code ?: 0)
        holder.bind(dataset[position], weatherCode)
    }

    class LocationHolder(
            private val binding: LocationRowViewBinding, private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.layout.setOnClickListener(this)
        }

        fun bind(data: LocationWithWeathers, weatherCode: WeatherCode) {
            val location = data.location
            val weather = data.weathers.getOrNull(0)

            binding.icon.setImageResource(weatherCode.getIcon(location))
            binding.name.text = location.name

            binding.temp.text = context.getString(R.string.temp_short_celsium, weather?.temp ?: 0)
            binding.wind.text = context.getString(R.string.wind_speed_short_ms, weather?.windSpeed ?: 0)
            binding.layout.tag = location
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(binding.layout.tag as Location)
        }
    }
}
