package com.example.james.planificador.GUI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.james.planificador.R;

public class Cambio_Estilo extends AppCompatActivity {


    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;

    ActionBar mActionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio__estilo);


        mActionBar = getSupportActionBar();



        //root = findViewById(R.id.root);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            Toast.makeText(this, "The device has no light sensor !", Toast.LENGTH_SHORT).show();
            finish();
        }

        //getSupportActio
// max value for light sensor
        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                mActionBar.setTitle("Luminosidad : " + value + " lx");

                // between 0 and 255
                int newValue = (int) (255f * value / maxValue);

                //getSupportActionBar().setc
                //mActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                mActionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(newValue, newValue, newValue)));
                //mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayShowTitleEnabled(true);
                //getWindow().getDecorView().setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}
