package com.anadi.weatherinfo.view.ui.locations

import android.content.Context
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnCreateContextMenuListener
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.databinding.LocationRowViewBinding
import com.anadi.weatherinfo.view.ui.locations.LocationAdapter.LocationHolder

class LocationAdapter(private val listener: OnLocationSelectedListener) : RecyclerView.Adapter<LocationHolder>() {
    interface OnLocationSelectedListener {
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
        holder.bind(dataset[position])
    }

    class LocationHolder(private val binding: LocationRowViewBinding, private val listener: OnLocationSelectedListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
        init {
            binding.layout.setOnClickListener(this)
            binding.layout.setOnCreateContextMenuListener(this)
        }

        fun bind(data: LocationWithWeathers) {
            val location = data.location
            val weather = data.weathers.getOrNull(0)

            binding.icon.setImageResource(IconMap.getIconId("01d"))
            binding.name.text = context.getString(R.string.location_name, location.city, location.country.code)

            binding.temp.text = context.getString(R.string.temp_short_celsium, weather?.temp ?: 0)
            binding.wind.text = context.getString(R.string.wind_speed_short_ms, weather?.windSpeed ?: 0)
            binding.layout.tag = location
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(binding.layout.tag as Location)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
            val popup = PopupMenu(v.context, v)
            popup.menuInflater.inflate(R.menu.menu_context, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            listener.onMenuAction(binding.layout.tag as Location, item)
            return false
        }
    }
}