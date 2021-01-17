package com.anadi.weatherinfo.view.ui.providers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.weather.WeatherCode
import com.anadi.weatherinfo.data.weather.WeatherCodes
import com.anadi.weatherinfo.databinding.ProviderViewBinding
import com.anadi.weatherinfo.utils.DateFormats

class ProvidersAdapter(private val listener: Listener, private val weatherCodes: WeatherCodes) :
        RecyclerView.Adapter<ProvidersAdapter.ProviderHolder>() {
    interface Listener {
        fun onSelected(weather: Weather)
    }

    var dataset = emptyList<Weather>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProviderHolder {

        val binding = ProviderViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProviderHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ProviderHolder, position: Int) {
        val weather = dataset[position]
        holder.bind(weather, weatherCodes.from(weather.code))
    }

    class ProviderHolder(
            private val binding: ProviderViewBinding,
            private val listener: Listener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var weather: Weather

        init {
            binding.layout.setOnClickListener(this)
        }

        fun bind(weather: Weather, weatherCode: WeatherCode) {
            this.weather = weather

            with(binding) {
                providerName.text = WeatherProvider.fromCode(weather.providerId).providerName
                lastUpdateTime.text = DateFormats.defaultTime.print(weather.dataCalcTimestamp)

                icon.setImageResource(weatherCode.iconDay)
                description.text = context.getString(weatherCode.description)

                temp.text = context.getString(R.string.temp_short_celsium, weather.temp)
                windSpeed.text = context.getString(R.string.wind_speed_short_ms, weather.windSpeed)
                windDirection.rotation = weather.windDegree.toFloat()

                pressure.text = context.getString(R.string.pressure_short, weather.pressure)
                humidity.text = context.getString(R.string.humidity_short, weather.humidity)
            }
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(weather)
        }
    }
}
