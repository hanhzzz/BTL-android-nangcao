package com.example.btl_adr_nangcao.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.btl_adr_nangcao.R;
import com.example.btl_adr_nangcao.databinding.ActivityLightSensorBinding;

public class LightSensor extends AppCompatActivity implements SensorEventListener {
    ActivityLightSensorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLightSensorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager != null ){
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

            if(lightSensor != null){
                sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else{
            Toast.makeText(this, "sensor not detected", Toast.LENGTH_SHORT).show();
        }

        //proximity sensor
        if(sensorManager != null ){
            Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            if(proximitySensor != null){
                sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        else{
            Toast.makeText(this, "sensor not detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            binding.textView12.setText("Light Values: "+ sensorEvent.values[0]);
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            binding.textView13.setText("Proximity Values: "+ sensorEvent.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}