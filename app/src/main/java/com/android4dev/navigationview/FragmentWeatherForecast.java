package com.android4dev.navigationview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.provider.forecastio.ForecastIOProviderType;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;

import java.util.List;

public class FragmentWeatherForecast extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the View
        View v = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        final TextView temp = (TextView) v.findViewById(R.id.temperatureTextF);
        final TextView condition = (TextView) v.findViewById(R.id.conditionTextF);
        final TextView city = (TextView) v.findViewById(R.id.cityNameTextF);

        // Get coordinates from super class
        MainActivity mainActivity = (MainActivity) getActivity();
        Double latitude = Double.valueOf(mainActivity.LATITUDE);
        Double longitude = Double.valueOf(mainActivity.LONGITUDE);

        // Initializing Weather Client Builder
        try {

            WeatherClient client = (new WeatherClient.ClientBuilder()).attach(getActivity())
                    .httpClient(WeatherDefaultClient.class)
                    .provider(new OpenweathermapProviderType())
                    .config(new WeatherConfig())
                    .build();

            client.getForecastWeather(new WeatherRequest(longitude, latitude), new WeatherClient.ForecastWeatherEventListener() {
                @Override
                public void onWeatherRetrieved(WeatherForecast forecast) {
                    List dayForecastList = forecast.getForecast();
                    DayForecast dForecast = new DayForecast();
                    for (dForecast : dayForecastList){
                        Weather weather = dForecast.weather;
                        long timestamp = dForecast.timestamp;
                    }
                }

                @Override
                public void onWeatherError(WeatherLibException wle) {
                    city.setText("Weather error - parsing data");
                    wle.printStackTrace();
                }

                @Override
                public void onConnectionError(Throwable t) {
                    city.setText("Connection error");
                    t.printStackTrace();
                }
            });

        } catch (Throwable t){
            t.printStackTrace();
        }



        return v;
    }

}