package com.example.rainfallapp.model;

import java.io.Serializable;

public class LocationModel implements Serializable {
    private double lat, lon;
    private String address, city, country;

    public LocationModel(double lat, double lon, String address, String city, String country) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
