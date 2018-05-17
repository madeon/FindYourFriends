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
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
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

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.Helpers.Group;
import com.example.mathias.findyourfriends.Helpers.User;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by mathi on 19-03-2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private FloatingActionButton myLocationButton;
    private ToastMaker toast;
    private GoogleMap map;
    private static LocationManager manager;
    private Location location;
    private double lat, lng;
    private DatabaseConnector databaseConnector;
    boolean addedMarkers = false;
    private TreeMap<String, User> userMap;
    private TreeMap<String, MarkerOptions> markerMap;
    private String groupID;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userMap = new TreeMap<>();
        markerMap = new TreeMap<>();
        //getUsersFromDatabase();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toast = new ToastMaker();
        databaseConnector = new DatabaseConnector("Users");
        getGroupID();
        getActivity().setTitle("Map");

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("RUN", "Size: " + userMap.size());
                        update();

            }
        });
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
                double lat = latlng.latitude;
                double lng = latlng.longitude;

                if(lat == 0.0 || lng == 0.0) {
                    toast.createToast(getActivity(), "GPS is still loading. Please try again");
                }
                else {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom((latlng), 16));
                }

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
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1001);

            return;
        }

        try {
            manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (Exception ex) {
            Log.d("Service Error", ex.getMessage());
        }
    }

    private void updateMarkers() {

        if (getActivity() == null) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                map.clear();

                for (Map.Entry<String, User> pair : userMap.entrySet()) {

                    try {
                        LatLng latlng = new LatLng(pair.getValue().getLat(), pair.getValue().getLng());
                        Marker marker = map.addMarker(markerMap.get(pair.getKey()));
                        marker.setPosition(latlng);
                    }

                    catch(Exception ex) {
                        continue;
                    }
                }
            }
        });
    }


    private void addMarkers() {

        if (addedMarkers == false) {

            if (getActivity() == null) {
                return;
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    for (Map.Entry<String, User> pair : userMap.entrySet()) {
                        LatLng latlng = new LatLng(pair.getValue().getLat(), pair.getValue().getLng());
                        markerMap.put(pair.getKey(), new MarkerOptions().position(latlng).title(pair.getValue().getName()));
                        map.addMarker(markerMap.get(pair.getKey()));
                    }
                }
            });
        }

        addedMarkers = true;
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
        databaseConnector.updateLocation(lat, lng);
    }

    private void update() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (userMap.size() > 0) {
                    addMarkers();
                    updateMarkers();
                }
            }
        }, 0, 1000);
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

    private void getGroupID() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();


        databaseConnector.getRef().child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groupID = dataSnapshot.getValue(User.class).getGroupID();

                getUsersFromDatabase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUsersFromDatabase() {

        FirebaseDatabase.getInstance().getReference().child("Users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            User user = snapshot.getValue(User.class);

                            if (user.getGroupID().equals(groupID)) {

                                if (!userMap.containsKey(user.getUID())) {
                                    userMap.put(user.getUID(), user);
                                    markerMap.put(user.getUID(), new MarkerOptions());

                                } else {

                                    userMap.get(user.getUID()).setLat(user.getLat());
                                    userMap.get(user.getUID()).setLng(user.getLng());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
