package com.example.rainfallapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

import com.example.rainfallapp.R;
import com.example.rainfallapp.model.weather.Daily;
import com.example.rainfallapp.model.weather.Temp;
import com.example.rainfallapp.model.weather.Weather;
import com.example.rainfallapp.utils.MyPreferences;
import com.example.rainfallapp.utils.Utils;

public class DayWeatherAdapter extends RecyclerView.Adapter<DayWeatherAdapter.Viewholder> {

    private Context context;
    private MyPreferences preferences;

    public DayWeatherAdapter(Context context) {
        this.context = context;
        preferences = new MyPreferences(context);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View view = Inflater.inflate(R.layout.item_view_daily, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Daily[] daily = preferences.getWeatherData().getDaily();
        Weather[] weatherList = daily[position].getWeather();
        Temp temperature = daily[position].getTemp();

        long dateInMillis = Long.parseLong(daily[position].getDt() + "000");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMillis);

        String date = String.valueOf(calendar.get(Calendar.DATE));
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        holder.tv_Day.setText(Utils.getDayFromDayNumber(day));
        holder.tv_Date.setText(date);
        holder.tv_WeatherTitle.setText(weatherList[0].getMain());
        holder.tv_TemperatureMax.setText(String.valueOf((int) Double.parseDouble(temperature.getMax())));
        holder.tv_TemperatureMin.setText(String.valueOf((int) Double.parseDouble(temperature.getMin())));
    }

    @Override
    public int getItemCount() {
        return preferences.getCurrentPreferences().getNoOfDays();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView tv_Day, tv_Date, tv_WeatherTitle, tv_TemperatureMax, tv_TemperatureMin;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tv_Day = itemView.findViewById(R.id.tv_Day);
            tv_Date = itemView.findViewById(R.id.tv_Date);
            tv_WeatherTitle = itemView.findViewById(R.id.tv_WeatherTitle);
            tv_TemperatureMax = itemView.findViewById(R.id.tv_TemperatureMax);
            tv_TemperatureMin = itemView.findViewById(R.id.tv_TemperatureMin);
        }
    }
}
