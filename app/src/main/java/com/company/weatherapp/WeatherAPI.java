package com.company.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=99e6a68f917cf2df5fd4f6298e44ff48&units=metric")
    Call<OpenWeatherMap> getWeatherWithLocation(@Query("lat")double lat, @Query("lon")double lon);

    @GET("weather?appid=99e6a68f917cf2df5fd4f6298e44ff48&units=metric")
    Call<OpenWeatherMap> getWeatherWithCityName(@Query("q")String name);
}
