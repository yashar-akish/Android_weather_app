package com.company.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class weatherActivity extends AppCompatActivity {

    private TextView cityWeather, temperatureWeather, conditionWeather, humidityWeather,
            maxTempWeather, minTempWeather, pressureWeather, windWeather;
    private ImageView imageViewWeather;
    private Button search;
    private EditText cityEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityWeather = findViewById(R.id.cityTv_weather);
        temperatureWeather = findViewById(R.id.tempTv_weather);
        conditionWeather = findViewById(R.id.conditionTv_weather);
        humidityWeather = findViewById(R.id.humidityTv_weather);
        maxTempWeather = findViewById(R.id.maxTempTv_weather);
        minTempWeather = findViewById(R.id.minTempTv_weather);
        pressureWeather = findViewById(R.id.pressureTv_weather);
        windWeather = findViewById(R.id.windTv_weather);
        imageViewWeather = findViewById(R.id.imageView_weather);
        search = findViewById(R.id.searchBtn);
        cityEt = findViewById(R.id.cityEt);

        cityWeather.setVisibility(View.INVISIBLE);
        temperatureWeather.setVisibility(View.INVISIBLE);
        conditionWeather.setVisibility(View.INVISIBLE);
        humidityWeather.setVisibility(View.INVISIBLE);
        maxTempWeather.setVisibility(View.INVISIBLE);
        minTempWeather.setVisibility(View.INVISIBLE);
        pressureWeather.setVisibility(View.INVISIBLE);
        windWeather.setVisibility(View.INVISIBLE);
        imageViewWeather.setVisibility(View.INVISIBLE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = cityEt.getText().toString();
                getWeatherData(cityName);
                cityEt.setText("");
            }
        });
    }

    public void getWeatherData(String name) {
        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call = weatherAPI.getWeatherWithCityName(name);

        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {

                cityWeather.setVisibility(View.VISIBLE);
                temperatureWeather.setVisibility(View.VISIBLE);
                conditionWeather.setVisibility(View.VISIBLE);
                humidityWeather.setVisibility(View.VISIBLE);
                maxTempWeather.setVisibility(View.VISIBLE);
                minTempWeather.setVisibility(View.VISIBLE);
                pressureWeather.setVisibility(View.VISIBLE);
                windWeather.setVisibility(View.VISIBLE);
                imageViewWeather.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    cityWeather.setText(response.body().getName() + " , " + response.body().getSys().getCountry());
                    temperatureWeather.setText(response.body().getMain().getTemp() + " °C");
                    conditionWeather.setText(response.body().getWeather().get(0).getDescription());
                    humidityWeather.setText(" : " + response.body().getMain().getHumidity() + "%");
                    maxTempWeather.setText(" : " + response.body().getMain().getTempMax() + " °C");
                    minTempWeather.setText(" : " + response.body().getMain().getTempMin() + " °C");
                    pressureWeather.setText(" : " + response.body().getMain().getPressure());
                    windWeather.setText(" : " + response.body().getWind().getSpeed());

                    String iconCode = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/" + iconCode + "@2x.png")
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageViewWeather);
                } else {
                    Toast.makeText(weatherActivity.this, "City not found, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });
    }
}