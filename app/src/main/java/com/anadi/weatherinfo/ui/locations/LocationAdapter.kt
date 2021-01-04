package com.anadi.weatherinfo.ui.locations

import android.content.Context
import android.content.res.Resources
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.view.View.OnCreateContextMenuListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.repository.IconMap
import com.anadi.weatherinfo.repository.LocationInfo
import com.anadi.weatherinfo.ui.locations.LocationAdapter.LocationHolder

class LocationAdapter(context: Context, private val listener: OnLocationSelectedListener) : RecyclerView.Adapter<LocationHolder>() {
    interface OnLocationSelectedListener {
        fun onSelected(locationInfo: LocationInfo)
        fun onMenuAction(locationInfo: LocationInfo, item: MenuItem)
    }

    private val res: Resources
    var locations = emptyList<LocationInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_row_view, parent, false)
        return LocationHolder(view)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationHolder, position: Int) {
        val current = locations[position]
        holder.icon.setImageResource(IconMap.getIconId(current.info.weather[0].icon))
        holder.name.text = current.cityName
        holder.wind.text = res.getString(R.string.wind_speed_ms, current.info.wind.speed)
        holder.temp.text = res.getString(R.string.temp_celsium, current.info.main.temp)
        holder.containerView.tag = current
    }

    inner class LocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {
        var containerView: LinearLayout
        var name: TextView
        var icon: ImageView
        var wind: TextView
        var temp: TextView
        override fun onClick(v: View) {
            val position = adapterPosition
            listener.onSelected(locations[position])
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
            val popup = PopupMenu(v.context, v)
            popup.menuInflater.inflate(R.menu.menu_context, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            listener.onMenuAction(locations[adapterPosition], item)
            return false
        }

        init {
            containerView = itemView.findViewById(R.id.location_row)
            containerView.setOnClickListener(this)
            containerView.setOnCreateContextMenuListener(this)
            icon = itemView.findViewById(R.id.location_row_icon)
            name = itemView.findViewById(R.id.location_row_name)
            wind = itemView.findViewById(R.id.location_row_wind)
            temp = itemView.findViewById(R.id.location_row_temp)
        }
    }

    init {
        res = context.resources
    }
}