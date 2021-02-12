package com.example.rainfallapp.model;

public class PreferencesModel {

    private long time;
    private int noOfDays;
    private LocationModel locationModel;

    public PreferencesModel() {
    }

    public PreferencesModel(long time, int noOfDays, LocationModel locationModel) {
        this.time = time;
        this.noOfDays = noOfDays;
        this.locationModel = locationModel;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }
}
