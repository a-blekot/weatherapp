package com.anadi.weatherinfo.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.weatherinfo.details.DetailsActivity;
import com.anadi.weatherinfo.repository.IconMap;
import com.anadi.weatherinfo.repository.LocationInfo;
import com.anadi.weatherinfo.R;

import java.util.*;

import timber.log.Timber;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationAdapterHolder> {

    static class LocationAdapterHolder extends RecyclerView.ViewHolder {

        public LinearLayout containerView;
        public TextView cityNameTextView;
        public ImageView iconImageView;
        public TextView windTextView;
        public TextView temperatureTextView;

        LocationAdapterHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.city_row);

            iconImageView = itemView.findViewById(R.id.city_row_icon_image_view);
            cityNameTextView = itemView.findViewById(R.id.city_row_name_text_view);
            windTextView = itemView.findViewById(R.id.city_row_wind_text_view);
            temperatureTextView = itemView.findViewById(R.id.city_row_temp_text_view);

            containerView.setOnClickListener(v -> {
                final LocationInfo current = (LocationInfo) containerView.getTag();

                Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                intent.putExtra("id", current.getId());

                v.getContext().startActivity(intent);
            });
        }

    }

    private Resources res;
    private MainActivityContract.Presenter presenter;
    private ArrayList<LocationInfo> locations = new ArrayList<>();
    private Context context;

    public LocationAdapter(Context context) {

        presenter = new MainPresenter(context);
        presenter.loadLocations(context);
        res = context.getResources();
        this.context = context;

        updateLocations();
    }

    public void updateLocations() {
        locations = presenter.getLocations();
        Timber.d( "cities = %s", locations);
        notifyDataSetChanged();
    }

    public void loadData() {
        presenter.loadData(context);
    }

    public void saveData() {
        presenter.saveData(context);
    }

    @NonNull
    @Override
    public LocationAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);

        return new LocationAdapterHolder(view);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapterHolder holder, int position) {
        LocationInfo current = locations.get(position);
        holder.iconImageView.setImageResource(IconMap.getIconId(current.getInfo().weather.get(0).icon));
        holder.cityNameTextView.setText(current.getCityName());
        holder.windTextView.setText(res.getString(R.string.wind_speed_ms, current.getInfo().wind.speed));
        holder.temperatureTextView.setText(res.getString(R.string.temp_celsium, current.getInfo().main.temp));

        holder.containerView.setTag(current);
    }
}
