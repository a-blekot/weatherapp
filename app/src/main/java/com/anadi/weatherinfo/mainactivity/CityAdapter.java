package com.anadi.weatherinfo.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anadi.weatherinfo.CityActivity;
import com.anadi.weatherinfo.CityInfo;
import com.anadi.weatherinfo.R;

import java.util.*;

import timber.log.Timber;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterHolder> {

    static class CityAdapterHolder extends RecyclerView.ViewHolder {

        public LinearLayout containerView;
        public TextView cityNameTextView;
        public ImageView iconImageView;
        public TextView windTextView;
        public TextView temperatureTextView;

        CityAdapterHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.city_row);

            iconImageView = itemView.findViewById(R.id.city_row_icon_image_view);
            cityNameTextView = itemView.findViewById(R.id.city_row_name_text_view);
            windTextView = itemView.findViewById(R.id.city_row_wind_text_view);
            temperatureTextView = itemView.findViewById(R.id.city_row_temp_text_view);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = containerView.getTag();
                    CityInfo current;

                    if (!(tag instanceof CityInfo)) {
                        Log.d("anadi", "onClick :: containerView.getTag() is not CityInfo.class");
                        return;
                    }

                    current = (CityInfo) tag;
                    Intent intent = new Intent(v.getContext(), CityActivity.class);
                    intent.putExtra("id", current.getId());

                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    private MainActivityContract.Presenter presenter;

    public CityAdapter(Context context) {

        presenter = new MainPresenter(context);
        presenter.loadLocations();

//        for (int i = 0; i < 8; i++) {
//            Country c = Location.getRandomCountry();
//            if (null == c)
//                return;
//
//            //cities.add(new CityInfo(Location.getRandomCity(c.toString()), c));
//
////            CitiesCash.add(Location.getRandomCity(c.toString()), c);
//        }

        updateCities();
    }

    public void updateCities() {
        cities = presenter.getCities();
        Timber.d( "cities = " + cities);
        notifyDataSetChanged();
    }

    private ArrayList<CityInfo> cities = new ArrayList<>();

    @NonNull
    @Override
    public CityAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_row, parent, false);

        return new CityAdapterHolder(view);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapterHolder holder, int position) {
        CityInfo current = cities.get(position);
        holder.iconImageView.setImageResource(R.drawable.clear_sky);
        holder.cityNameTextView.setText(current.getCityName());
        holder.windTextView.setText(current.getInfo().getWindSpeed() + " m/s");
        holder.temperatureTextView.setText(current.getInfo().getTemperature() + " cels");

        holder.containerView.setTag(current);
    }
}
