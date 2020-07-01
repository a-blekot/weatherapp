package com.anadi.weatherinfo;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterHolder> {

    static class CityAdapterHolder extends RecyclerView.ViewHolder {

        public LinearLayout containerView;
        public TextView cityNameTextView;
        public TextView windTextView;
        public TextView temperatureTextView;

        CityAdapterHolder(@NonNull View itemView) {
            super(itemView);

            containerView = itemView.findViewById(R.id.city_row);
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
                    intent.putExtra("city", current.getCityName());
                    intent.putExtra("country", current.getCountryName());

                    v.getContext().startActivity(intent);
                }
            });
        }

    }

//    public CityAdapter() {
//
//    }

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
        holder.cityNameTextView.setText(current.getCityName());
        holder.windTextView.setText(current.getInfo().getWindSpeed());
        holder.temperatureTextView.setText(current.getInfo().getWindSpeed());

        holder.containerView.setTag(current);
    }
}
