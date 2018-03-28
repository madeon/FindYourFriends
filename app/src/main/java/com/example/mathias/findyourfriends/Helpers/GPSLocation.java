package com.example.mathias.findyourfriends.Helpers;

import java.util.Date;

/**
 * Created by mathi on 21-03-2018.
 */

public class GPSLocation {

    private String uid, lat, lng;
    private Date timestamp;

    public GPSLocation(String uid, String lat, String lng, Date timestamp) {
        this.uid = uid;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
