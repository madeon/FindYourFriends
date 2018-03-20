package com.example.mathias.findyourfriends.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

/**
 * Created by mathi on 20-03-2018.
 */

public class LocationService implements LocationListener {

    private static LocationService instance = null;
    private static LocationManager manager;
    private double longitude;
    private double latitude;
    private Location location;


    //Singleton instantiation of the class
    public static LocationService getLocationManager(Context context) {
        if (instance == null) {
            instance = new LocationService(context);
        }
        return instance;
    }

    //Private constructor
    private LocationService(Context context) {

    }

    private void initializeService(Context context) {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //ACCESS DENIED

            return;
        }

        else {
            manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
