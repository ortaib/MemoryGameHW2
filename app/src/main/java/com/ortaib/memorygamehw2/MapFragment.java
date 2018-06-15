package com.ortaib.memorygamehw2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private Button btnMap,btnList,btnBack;
    private final int LIST_FRAGMENT = 0;
    private final int MAP_FRAGMENT = 1;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private LocationManager locationManager;
    DatabaseHelper myDatabasehelper;
    private final static int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private final String TAG = "MapFragment";
    private final float DEFUALT_ZOOM = 0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);
        myDatabasehelper = new DatabaseHelper((ScoreAndMapFragmentsActivity)getContext());
        btnMap = (Button) view.findViewById(R.id.mapfragment);
        btnList = (Button)view.findViewById(R.id.listfragment);
        btnBack = (Button)view.findViewById(R.id.back);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScoreAndMapFragmentsActivity)getActivity()).setViewPager(LIST_FRAGMENT);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ScoreAndMapFragmentsActivity)getActivity()).setViewPager(MAP_FRAGMENT);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((ScoreAndMapFragmentsActivity)getActivity(),HomePageActivity.class);
                intent.putExtra("year",0);
                startActivity(intent);
            }
        });
        initMap();
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        mMap.setMyLocationEnabled(true);
        putMarkers();
    }

    private void initMap() {
        mMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        if(mMapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mMapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,mMapFragment).commit();
        }
        mMapFragment.getMapAsync(this);
        Log.d(TAG, "initMap: initializing map");
    }
    private void putMarkers(){
        Cursor data = myDatabasehelper.getData();
        double lat,lon;
        String name;
        int num=1,score;
        while(data.moveToNext()){
            name = data.getString(1);
            score = data.getInt(2);
            lat = data.getDouble(3);
            lon = data.getDouble(4);
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("#"+num).snippet("name: " +name+
                    ", Score : "+score));
            num++;
        }
    }
    private void moveCamera(LatLng lat,float zoom){
        Log.d(TAG, "moveCamera: moving the camere to : "+lat.latitude +", "+lat.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat,zoom));
    }
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the device current location");
        locationManager = (LocationManager) ((ScoreAndMapFragmentsActivity)getActivity()).
                getSystemService(Context.LOCATION_SERVICE);
            try{
                @SuppressLint("MissingPermission") Location currentLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFUALT_ZOOM);
            }catch(SecurityException e){
                Log.e(TAG, "getDeviceLocation: SecurityException: "+e.getMessage());
            }
    }

}
