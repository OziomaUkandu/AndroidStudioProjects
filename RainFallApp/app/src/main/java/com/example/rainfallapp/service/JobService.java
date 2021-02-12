package com.example.rainfallapp.service;

import android.app.Notification;
import android.app.job.JobParameters;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.example.rainfallapp.App;
import com.example.rainfallapp.R;
import com.example.rainfallapp.model.PreferencesModel;
import com.example.rainfallapp.model.weather.Data;
import com.example.rainfallapp.model.weather.Rain;
import com.example.rainfallapp.model.weather.Weather;
import com.example.rainfallapp.network.ApiClient;
import com.example.rainfallapp.network.ApiInterface;
import com.example.rainfallapp.utils.Constants;
import com.example.rainfallapp.utils.MyPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobService extends android.app.job.JobService {

    public static final String TAG = "RainFallApp";
    private boolean jobCancelled = false;
    private MyPreferences preferences;
    private PreferencesModel model;
    private NotificationManagerCompat notificationManager;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job Started");
        notificationManager = NotificationManagerCompat.from(this);
        doBackgroundWork(jobParameters);

        /*Set return to true for long running tasks and tell system too keep device awake*/
        return true;
    }

    private void doBackgroundWork(JobParameters params) {

        preferences = new MyPreferences(getApplicationContext());
        model = preferences.getCurrentPreferences();

        new Thread(() -> {
            /*Here the API will be called to get data from the server*/
            String url = "onecall?"
                    + "lat=" + model.getLocationModel().getLat()
                    + "&lon=" + model.getLocationModel().getLon()
                    + "&appid=" + Constants.APP_ID
                    + "&units=" + Constants.METRIC_OR_CELSIUS;

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> jsonObjectCall = apiInterface.getWeatherDataJson(url);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Log.d(TAG, "onResponse: ");
                    Log.d(TAG, "Response Code: " + response.code());

                    if (response.isSuccessful()) {
                        if (response.code() == 200 && response.body() != null) {
                            JsonObject object = response.body();
                            Gson gson = new Gson();
                            Data data = gson.fromJson(response.body(), Data.class);
                            JsonObject current = object.get("current").getAsJsonObject();


                            try {
                                JsonObject rain = current.get("rain").getAsJsonObject();
                                String h = rain.get("1h").getAsString();
                                Log.d(TAG, "h -> " + h);
                                Rain rain1 = new Rain();
                                rain1.set1h(h);
                                data.getCurrent().setRain(rain1);
                            } catch (Exception e) {

                            }
                            /*Save Data in Preferences*/
                            preferences.saveWeatherData(data);

                            /*how Notification if rain object has data*/
                            if (data.getCurrent().getRain() != null) {
                                Weather[] weatherList = data.getCurrent().getWeather();
                                String rainDescription = weatherList[0].getDescription() + " " + data.getCurrent().getRain().get1h() + " mm";
                                sendNotification("Rain", rainDescription);
                            }

                            Log.d(TAG, "Data -> " + data.toString());
                        }
                        Log.d(TAG, "Job Finished");
                        jobFinished(params, false);

                    } else {
                        Log.d(TAG, "Unsuccessful");
                        Log.d(TAG, "Job Finished");
                        jobFinished(params, true);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Log.d(TAG, "Job Finished");
                    jobFinished(params, true);
                }
            });

        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job Cancelled before completion");
        jobCancelled = true;
        return true;
    }

    public void sendNotification(String title, String message) {
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        //If the first param is same it will overwrite
        notificationManager.notify(1, notification);
    }
}