package com.anadi.weatherinfo.mainactivity;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.weatherinfo.R;
import com.anadi.weatherinfo.repository.IconMap;
import com.anadi.weatherinfo.repository.LocationInfo;

import java.util.ArrayList;

import timber.log.Timber;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {
    public interface OnLocationSelectedListener {
        void onSelected(LocationInfo locationInfo);

        void onMenuAction(LocationInfo locationInfo, MenuItem item);
    }
    @NonNull
    private final OnLocationSelectedListener listener;
    private final Resources res;
    private final MainActivityContract.Presenter presenter;
    private ArrayList<LocationInfo> locations = new ArrayList<>();

    public LocationAdapter(Context context, OnLocationSelectedListener listener,
                           MainActivityContract.Presenter presenter) {
        this.listener = listener;
        this.presenter = presenter;

        res = context.getResources();
        updateLocations();
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_row, parent, false);

        return new LocationHolder(view);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        LocationInfo current = locations.get(position);
        holder.icon.setImageResource(IconMap.getIconId(current.getInfo().weather.get(0).icon));
        holder.name.setText(current.getCityName());
        holder.wind.setText(res.getString(R.string.wind_speed_ms, current.getInfo().wind.speed));
        holder.temp.setText(res.getString(R.string.temp_celsium, current.getInfo().main.temp));

        holder.containerView.setTag(current);
    }

    public void updateLocations() {
        locations = presenter.getLocations();
        Timber.d("cities = %s", locations);
        notifyDataSetChanged();
    }

    class LocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, PopupMenu.OnMenuItemClickListener {

        public LinearLayout containerView;
        public TextView name;
        public ImageView icon;
        public TextView wind;
        public TextView temp;

        LocationHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.location_row);
            containerView.setOnClickListener(this);
            containerView.setOnCreateContextMenuListener(this);

            icon = itemView.findViewById(R.id.location_row_icon);
            name = itemView.findViewById(R.id.location_row_name);
            wind = itemView.findViewById(R.id.location_row_wind);
            temp = itemView.findViewById(R.id.location_row_temp);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onSelected(locations.get(position));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            popup.getMenuInflater().inflate(R.menu.menu_context, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            listener.onMenuAction(locations.get(getAdapterPosition()), item);
            return false;
        }
    }
}