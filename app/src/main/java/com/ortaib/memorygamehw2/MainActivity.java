package com.ortaib.memorygamehw2;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private TextView date,name;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private DatePickerDialog.OnDateSetListener date_listener;
    private SharedPreferences mPreferences;
    private boolean locationPermissionGranted=false;
    private LocationManager locationManager;
    private SharedPreferences.Editor mEditor;
    private Calendar cal;
    private boolean dateIsSet=false;
    private int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        getLocationPermission();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        name = (TextView)findViewById(R.id.name);
        date = (TextView)findViewById(R.id.dateofbirth);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,date_listener,year,month,day);
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });
        date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i1, int i2, int i3) {
                int tempMonth=i2+1;
                date.setText(i3 + "/" +tempMonth+"/"+i1);
                year=i1;
                month=i2;
                day=i3;
                dateIsSet=true;
            }
        };

    }

    public void SendMessage(View view) {
        if(dateIsSet==true && name.length() != 0 && locationPermissionGranted == true){
            Intent intent = new Intent(this,HomePageActivity.class);
            mEditor.putString(getString(R.string.name),name.getText().toString());
            mEditor.commit();
            mEditor.putInt(getString(R.string.year),this.year);
            mEditor.commit();
            mEditor.putInt(getString(R.string.month),this.month);
            mEditor.commit();
            mEditor.putInt(getString(R.string.day),this.day);
            mEditor.commit();

            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            if(dateIsSet == false) {
                builder.setTitle("Date not set");
                builder.setMessage("Date wasn't selected");
            }
            else if(name.length() == 0 ){
                builder.setTitle("name not set");
                builder.setMessage("name wasn't selected");
            }
            else{
                builder.setTitle("you need to allow location premission");
                getLocationPermission();
            }
             builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
                 builder.setIcon(android.R.drawable.ic_dialog_alert);
                 builder.show();

        }
    }
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: requesting permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult: called");

        locationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted ");
                    locationPermissionGranted = true;

                }
        }
    }
}
