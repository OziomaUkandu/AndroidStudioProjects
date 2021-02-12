package com.example.rainfallapp.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.rainfallapp.adapter.DayWeatherAdapter;
import com.example.rainfallapp.databinding.ActivityMainBinding;
import com.example.rainfallapp.dialog.PreferencesDialog;
import com.example.rainfallapp.interfaces.LocationPickListener;
import com.example.rainfallapp.interfaces.SaveClickListener;
import com.example.rainfallapp.model.LocationModel;
import com.example.rainfallapp.model.PreferencesModel;
import com.example.rainfallapp.model.weather.Data;
import com.example.rainfallapp.model.weather.Weather;
import com.example.rainfallapp.receiver.AlarmReceiver;
import com.example.rainfallapp.utils.Constants;
import com.example.rainfallapp.utils.MyPreferences;


/*When all preferences are set
 * 1. Repeating alarm start which leads to Alarm Receiver when specified time comes
 * 2. From Alarm Receiver, a job scheduler is created which leads to Job Service
 * to call the API and get data, when it finishes getting data; the scheduler is stopped
*/

public class MainActivity extends AppCompatActivity implements LocationPickListener, SaveClickListener {

    private ActivityMainBinding binding;
    private Context context;
    private MyPreferences preferences;
    private PreferencesDialog preferencesDialog;

    SimpleDateFormat hourFormat = new SimpleDateFormat("hh");
    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm a");

    public static final String TAG = "RainFallApp";
    private PreferencesModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setCLickListener();
        setCurrentData();
    }

    private void init() {
        context = MainActivity.this;
        preferencesDialog = new PreferencesDialog(context, this, this);
        preferences = new MyPreferences(context);
        if (preferences.getCurrentPreferences() == null) {
            preferencesDialog.showFirstTime();
        }
    }

    private void setCLickListener() {
        binding.ivSettings.setOnClickListener(v -> preferencesDialog.show());
    }

    @Override
    public void selectLocation() {
        startActivityForResult(new Intent(context, GooglemapActivity.class), Constants.GET_LOCATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GET_LOCATION) {
            if (data != null) {
                LocationModel locationModel = (LocationModel) data.getSerializableExtra(Constants.LOCATION_DATA);
                preferencesDialog.setLocation(locationModel);
            }
        }
    }

    @Override
    public void savePressed(PreferencesModel model) {
        preferences.saveCurrentPreferences(model);
        cancelAlarm();
        cancelJob();
        startRepeatingAlarm();
        setCurrentData();
    }

    private void startRepeatingAlarm() {

        int hours = Integer.parseInt(hourFormat.format(preferences.getCurrentPreferences().getTime()));
        int minutes = Integer.parseInt(minuteFormat.format(preferences.getCurrentPreferences().getTime()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Constants.ALARM_REQUEST_CODE, intent, 0);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), calendar.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 15, pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, Constants.ALARM_REQUEST_CODE, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(Constants.JOB_ID);
        Log.d(TAG, "Job Cancelled");
    }

    private void setCurrentData() {
        if (preferences.getWeatherData() != null) {
            binding.tvNoData.setVisibility(View.GONE);
            Data data = preferences.getWeatherData();
            Weather[] weatherList = data.getCurrent().getWeather();

            binding.tvWeatherTitle.setText(weatherList[0].getMain());
            binding.tvCityAndCountry.setText(preferences.getCurrentPreferences().getLocationModel().getCity() + ", " + preferences.getCurrentPreferences().getLocationModel().getCountry());

            binding.tvTemperature.setText(String.valueOf((int) Double.parseDouble(data.getCurrent().getTemp())));

            if (data.getCurrent().getRain() != null) {
                String rainDescription = weatherList[0].getDescription() + " " + data.getCurrent().getRain().get1h() + " mm";
                binding.tvRainDescription.setText(rainDescription);
            }

            DayWeatherAdapter dayWeatherAdapter = new DayWeatherAdapter(context);
            binding.rclViewDayWeather.setAdapter(dayWeatherAdapter);

//            binding.tvLastUpdated.setText(String.valueOf(sdf.format(preferences.getLastUpdated())));

        } else {
            if (preferences.getCurrentPreferences() != null) {
                binding.tvNoData.setVisibility(View.VISIBLE);
                binding.tvNoData.setText("Data will be collected at " + timeFormat.format(preferences.getCurrentPreferences().getTime()));
            }
        }
    }
}