package com.example.rainfallapp.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.rainfallapp.R;
import com.example.rainfallapp.interfaces.LocationPickListener;
import com.example.rainfallapp.interfaces.SaveClickListener;
import com.example.rainfallapp.model.LocationModel;
import com.example.rainfallapp.model.PreferencesModel;
import com.example.rainfallapp.utils.MyPreferences;
import com.example.rainfallapp.utils.Utils;


public class PreferencesDialog implements TimePickerDialog.OnTimeSetListener {

    private Context context;
    private Dialog dialog;

    private TextInputEditText editText;
    private ImageView iv_Back;
    private TextView tv_Location, tv_Time, tv_Days;
    private Button btn_SavePreferences;

    private LocationPickListener locationPickListener;
    private SaveClickListener saveClickListener;

    private MyPreferences preferences;

    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    private PreferencesModel preferencesModel = new PreferencesModel();

    private boolean isFirstTime = false, isLocation = false, isDays = false, isTime = false;

    private LocationModel locationModel;

    public PreferencesDialog(Context context, LocationPickListener locationPickListener, SaveClickListener saveClickListener) {
        this.context = context;
        this.locationPickListener = locationPickListener;
        this.saveClickListener = saveClickListener;

        preferences = new MyPreferences(context);

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_preferences);

        iv_Back = dialog.findViewById(R.id.iv_Back);
        tv_Location = dialog.findViewById(R.id.tv_Location);
        tv_Time = dialog.findViewById(R.id.tv_Time);
        tv_Days = dialog.findViewById(R.id.tv_Days);
        btn_SavePreferences = dialog.findViewById(R.id.btn_SavePreferences);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        iv_Back.setOnClickListener(v -> dialog.dismiss());

        tv_Location.setOnClickListener(v -> locationPickListener.selectLocation());

        tv_Time.setOnClickListener(v -> {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            int minute = Calendar.getInstance().get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), this, hour, minute, true);
            timePickerDialog.show();
            timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
        });

        tv_Days.setOnClickListener(v -> showDaysMenu(v));

        btn_SavePreferences.setOnClickListener(v -> {
            if (isFirstTime) {
                if (!isLocation) {
                    Utils.setError(tv_Location, "Please select Location");
                    return;
                }
                if (!isTime) {
                    Utils.setError(tv_Time, "Please select Time of the schedule");
                    return;
                }
                if (!isDays) {
                    Utils.setError(tv_Days, "Please select Number of days for which you want to show data");
                    return;
                }
            }
            dialog.dismiss();
            saveClickListener.savePressed(preferencesModel);
        });
    }

    public void setCancelable(boolean flag) {
        dialog.setCancelable(flag);
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void show() {
        preferencesModel = preferences.getCurrentPreferences();
        iv_Back.setVisibility(View.VISIBLE);
        tv_Location.setText(preferencesModel.getLocationModel().getAddress());
        tv_Time.setText(String.valueOf(timeFormat.format(preferencesModel.getTime())));
        tv_Days.setText(String.valueOf(preferencesModel.getNoOfDays()));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void showFirstTime() {
        isFirstTime = true;
        iv_Back.setVisibility(View.GONE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showDaysMenu(View view) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.days, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            isDays = true;
            switch (item.getItemId()) {
                case R.id.one:
                    tv_Days.setText(context.getString(R.string._1));
                    preferencesModel.setNoOfDays(1);
                    break;
                case R.id.two:
                    tv_Days.setText(context.getString(R.string._2));
                    preferencesModel.setNoOfDays(2);
                    break;
                case R.id.three:
                    tv_Days.setText(context.getString(R.string._3));
                    preferencesModel.setNoOfDays(3);
                    break;
                case R.id.four:
                    tv_Days.setText(context.getString(R.string._4));
                    preferencesModel.setNoOfDays(4);
                    break;
                case R.id.five:
                    tv_Days.setText(context.getString(R.string._5));
                    preferencesModel.setNoOfDays(5);
                    break;
                case R.id.six:
                    tv_Days.setText(context.getString(R.string._6));
                    preferencesModel.setNoOfDays(6);
                    break;
                case R.id.seven:
                    tv_Days.setText(context.getString(R.string._7));
                    preferencesModel.setNoOfDays(7);
                    break;
            }
            return true;
        });
        popup.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        tv_Time.setText(timeFormat.format(c.getTime()));
        preferencesModel.setTime(c.getTimeInMillis());
        isTime = true;
    }

    public void setLocation(LocationModel locationModel) {
        this.locationModel = locationModel;
        tv_Location.setText(locationModel.getAddress());
        preferencesModel.setLocationModel(locationModel);
        isLocation = true;
    }
}
