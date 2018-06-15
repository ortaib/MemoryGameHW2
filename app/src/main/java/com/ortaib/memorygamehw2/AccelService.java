package com.ortaib.memorygamehw2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class AccelService extends Service implements SensorEventListener{
    private final IBinder accelBinder= new LocalAccelBinder();
    private final float MULT_FACTOR =  57.2957795f;
    private float sA,sR,sP;
    private int isSet=5;

    private final int ALLOWEDOFFSET=20;
    public AccelService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_UI);


        return accelBinder;
    }

    public boolean isOnPosition()
    {
        if(Math.abs(sP-pitch)<ALLOWEDOFFSET&&Math.abs(sR-roll)<ALLOWEDOFFSET&&Math.abs(sA-azimuth)<ALLOWEDOFFSET)
        {
            //Toast.makeText(this, "on pos", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    // Gravity rotational data
    private float gravity[];
    // Magnetic rotational data
    private float magnetic[]; //for magnetic rotational data
    private float accels[] = new float[3];
    private float mags[] = new float[3];
    private float[] values = new float[3];

    // azimuth, pitch and roll
    private float azimuth;
    private float pitch;
    private float roll;
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                mags = event.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accels = event.values.clone();
                break;
        }

        if (mags != null && accels != null) {
            gravity = new float[9];
            magnetic = new float[9];
            SensorManager.getRotationMatrix(gravity, magnetic, accels, mags);
            float[] outGravity = new float[9];
            SensorManager.remapCoordinateSystem(gravity, SensorManager.AXIS_X,SensorManager.AXIS_Z, outGravity);
            SensorManager.getOrientation(outGravity, values);

            azimuth = values[0] * MULT_FACTOR;
            pitch =values[1] * MULT_FACTOR;
            roll = values[2] * MULT_FACTOR;
            mags = null;
            accels = null;
            //
            if(isSet==0)
            {
                sA = azimuth;
                sP=pitch;
                sR=roll;
                isSet=10;
                Toast.makeText(this,"Start values: "+sA+", "+sP+", "+sR ,Toast.LENGTH_LONG).show();
            }
            else
            {
                if(isSet<10&&isSet>0)
                    isSet--;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private SensorManager mSensorManager;

    @Override
    public void onCreate() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    public class LocalAccelBinder extends Binder{
        AccelService getService(){
            return AccelService.this;
        }
    }
}

