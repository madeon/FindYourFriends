package com.example.mathias.findyourfriends.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by mathi on 19-03-2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private FloatingActionButton myLocationButton;
    private ToastMaker toast;
    private GoogleMap map;
    private Marker marker;
    private static LocationManager manager;
    private Location location;
    private double lat, lng;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toast = new ToastMaker();
        getActivity().setTitle("Map");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);



        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        myLocationButton = (FloatingActionButton) view.findViewById(R.id.myLocationButton);

        myLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LatLng latlng = new LatLng(lat, lng);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom((latlng), 16));
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        requestGpsPermission();
        map = googleMap;
    }

    private void requestGpsPermission() {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},1001);

            return;
        }

        try {
            manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }

        catch(Exception ex) {
            Log.d("Service Error", ex.getMessage());
        }
    }

    private void addMarker(LatLng latlng) {

        if(marker != null) {
            marker.remove();
        }

        marker = map.addMarker(new MarkerOptions().position(latlng));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();

        LatLng latlng = new LatLng(lat, lng);
        addMarker(latlng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        toast.createToast(getActivity(), "GPS is enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        toast.createToast(getActivity(), "GPS is disabled");
    }


}
