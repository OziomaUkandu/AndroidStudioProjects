package com.example.rainfallapp.receiver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.rainfallapp.service.JobService;
import com.example.rainfallapp.utils.Constants;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "RainFallApp";

    /*When specific time comes in the alarm manager, this onReceive is called*/
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "onReceive: ");
        scheduleJob(context);
    }

    private void scheduleJob(Context context) {
        ComponentName componentName = new ComponentName(context, JobService.class);
        JobInfo info = new JobInfo.Builder(Constants.JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true) // Keep job alive even after boot
                .setPeriodic(15 * 60 * 1000) // Job will be executed within this period at most once, this cant be less than 15
                .build();
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCde = scheduler.schedule(info);
        if (resultCde == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job Scheduled ");
        } else {
            Log.d(TAG, "Job Scheduling failed ");
        }
    }

    private void cancelJob(Context context) {
        JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(Constants.JOB_ID);
        Log.d(TAG, "Job Cancelled");
    }
}