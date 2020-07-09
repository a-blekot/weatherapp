package com.anadi.weatherinfo.repository;

import com.anadi.weatherinfo.repository.data.OpenWeatherMapInterface;
import com.anadi.weatherinfo.repository.data.WeatherInfo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class InfoLoader {
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "f9dee5683fdf51c7b611df7f57f26926";
    private static final String mUnits = "metric";
    private static String mLang = "en";

    private static final String TEST = API_URL + "weather/?q=Kiev,ua&appid=" + API_KEY + "&units=" + mUnits;

    private static volatile InfoLoader instance;
    private Retrofit mRetrofit;

    // Singleton with double check
    private InfoLoader() {
        mRetrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static InfoLoader getInstance() {
        InfoLoader result = instance;
        if (result == null) {
            synchronized (InfoLoader.class) {
                result = instance;
                if (result == null) {
                    instance = result = new InfoLoader();
                }
            }
        }
        return result;
    }

    public WeatherInfo load(final String cityName, final Country country) {
        try {
            String location = cityName + "," + country.code.toLowerCase();
            Timber.d("Trying to load weather info for: %s", location);
            Timber.d("TEST = %s", TEST);


            OpenWeatherMapInterface apiService = mRetrofit.create(OpenWeatherMapInterface.class);
            Call<WeatherInfo> call = apiService.getWeather(location, API_KEY, mUnits, mLang);
            Response<WeatherInfo> response = call.execute();

            int statusCode = response.code();
            WeatherInfo weatherInfo = response.body();

            Timber.d("Call (%s) statusCode = %d", location, statusCode);
            // код 200
            if (response.isSuccessful()) {
                Timber.d("Yeah baby!!!");
            }
            else {
                switch(statusCode) {
                    case 404:
                        // страница не найдена. можно использовать ResponseBody, см. ниже
                        break;
                    case 500:
                        // ошибка на сервере. можно использовать ResponseBody, см. ниже
                        break;
                }
                ResponseBody errorBody = response.errorBody();
                if (errorBody != null)
                    Timber.d("Error message: %s", errorBody.string());
            }

            Timber.d("Call (%s) statusCode = %d", location, statusCode);

            if (weatherInfo.weather == null)
                Timber.d("WARNING!!! weatherInfo.weather == null");
            else {
                Timber.d("It's ok somebody created list.");
                Timber.d("weather list size = %d", weatherInfo.weather.size());
            }

            return weatherInfo;
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}