package com.anadi.weatherinfo.view.ui.locations

import android.content.Context
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnCreateContextMenuListener
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.LocationInfo
import com.anadi.weatherinfo.databinding.LocationRowViewBinding
import com.anadi.weatherinfo.view.ui.locations.LocationAdapter.LocationHolder

class LocationAdapter(private val listener: OnLocationSelectedListener) : RecyclerView.Adapter<LocationHolder>() {
    interface OnLocationSelectedListener {
        fun onSelected(locationInfo: LocationInfo)
        fun onMenuAction(locationInfo: LocationInfo, item: MenuItem)
    }

    var locations = emptyList<LocationInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {

        val binding = LocationRowViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LocationHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        holder.bind(locations[position])
    }

    class LocationHolder(private val binding: LocationRowViewBinding, private val listener: OnLocationSelectedListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener, OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
        private lateinit var locationInfo: LocationInfo

        init {
            binding.layout.setOnClickListener(this)
            binding.layout.setOnCreateContextMenuListener(this)
        }

        fun bind(locationInfo: LocationInfo) {
            this.locationInfo = locationInfo
            binding.icon.setImageResource(IconMap.getIconId(locationInfo.info.weather[0].icon))
            binding.name.text = locationInfo.cityName
            binding.wind.text = context.getString(R.string.wind_speed_short_ms, locationInfo.info.wind.speed)
            binding.temp.text = context.getString(R.string.temp_short_celsium, locationInfo.info.main.temp)
            binding.layout.tag = locationInfo
        }

        private val context: Context
            get() = binding.root.context

        override fun onClick(v: View) {
            listener.onSelected(locationInfo)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
            val popup = PopupMenu(v.context, v)
            popup.menuInflater.inflate(R.menu.menu_context, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            listener.onMenuAction(locationInfo, item)
            return false
        }
    }
}