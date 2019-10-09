package com.fyp.ble.compass_test_1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class Compass implements SensorEventListener {
    private int compassValue;

    public Compass(){
        compassValue = 0;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.compassValue = (int) event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
