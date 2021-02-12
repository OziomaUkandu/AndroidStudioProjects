package com.example.rainfallapp.network;

import com.google.gson.JsonObject;

import com.example.rainfallapp.model.weather.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Call<Data> getWeatherData(@Url String url);

    @GET
    Call<JsonObject> getWeatherDataJson(@Url String url);

}
