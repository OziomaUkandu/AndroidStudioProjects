package com.example.rainfallapp.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static void setError(TextView textView, String errorMessage) {
        textView.setError(errorMessage);
    }

    public static void showShortToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    public static String getDayFromDayNumber(int day) {
        if (day == Calendar.SUNDAY) {
            return "SUN";
        } else if (day == Calendar.MONDAY) {
            return "MON";
        } else if (day == Calendar.TUESDAY) {
            return "TUE";
        } else if (day == Calendar.WEDNESDAY) {
            return "WED";
        } else if (day == Calendar.THURSDAY) {
            return "THU";
        } else if (day == Calendar.FRIDAY) {
            return "FRI";
        } else {
            return "SAT";
        }
    }
}
