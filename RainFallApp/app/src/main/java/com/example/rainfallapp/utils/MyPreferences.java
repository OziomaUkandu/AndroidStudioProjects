package com.example.rainfallapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.example.rainfallapp.model.PreferencesModel;
import com.example.rainfallapp.model.weather.Data;

public class MyPreferences {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static String PREF_NAME = "RainFallApp";
    private static String KEY_CURRENT_PREFERENCES = "KEY_CURRENT_PREFERENCES";
    private static String KEY_WEATHER_DATA = "KEY_WEATHER_DATA";
    private static String KEY_LAST_UPDATED = "KEY_LAST_UPDATED";

    public MyPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = preferences.edit();
    }

    public void saveCurrentPreferences(PreferencesModel model) {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString(KEY_CURRENT_PREFERENCES, json);
        editor.commit();
    }

    public PreferencesModel getCurrentPreferences() {
        Gson gson = new Gson();
        String json = preferences.getString(KEY_CURRENT_PREFERENCES, null);
        return gson.fromJson(json, PreferencesModel.class);
    }

    public void saveWeatherData(Data data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString(KEY_WEATHER_DATA, json);
        editor.putLong(KEY_LAST_UPDATED, System.currentTimeMillis());
        editor.commit();
    }

    public Data getWeatherData() {
        Gson gson = new Gson();
        String json = preferences.getString(KEY_WEATHER_DATA, null);
        return gson.fromJson(json, Data.class);
    }

    public long getLastUpdated() {
        return preferences.getLong(KEY_LAST_UPDATED, 0);
    }
}
