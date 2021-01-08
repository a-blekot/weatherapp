package com.anadi.weatherinfo.view.ui.providers

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.databinding.ProviderViewBinding

class ProvidersAdapter(private val listener: Listener) : RecyclerView.Adapter<ProvidersAdapter.ProviderHolder>() {
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
        holder.bind(dataset[position])
    }

    class ProviderHolder(private val binding: ProviderViewBinding, private val listener: Listener) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var weather: Weather

        init {
            binding.layout.setOnClickListener(this)
        }

        fun bind(weather: Weather) {
            this.weather = weather

            binding.providerName.text = WeatherProvider.fromCode(weather.providerId).providerName
            binding.lastUpdateTime.text = weather.dataCalcTimestamp.toString() // TODO timestamp convert to date

            binding.icon.setImageResource(IconMap.getIconId("01d"))
            binding.description.text = weather.code.toString() // TODO convert weather code to string

            binding.temp.text = context.getString(R.string.temp_short_celsium, weather.temp)
            binding.windSpeed.text = context.getString(R.string.wind_speed_short_ms, weather.windSpeed)
            binding.windDir.rotation = weather.windDegree.toFloat()

            binding.pressure.text = context.getString(R.string.pressure_short, weather.pressure)
            binding.humidity.text = context.getString(R.string.humidity_short, weather.humidity)
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(weather)
        }
    }
}