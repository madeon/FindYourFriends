package com.example.mathias.findyourfriends.Helpers;

/**
 * Created by mathi on 27-03-2018.
 */

public class User {

    private String UID;
    private String name;
    private String email;
    private double lat;
    private double lng;


    public User() {

    }

    public User(String UID, String email, String name, double lat, double lng) {
        this.UID = UID;
        this.email = email;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
