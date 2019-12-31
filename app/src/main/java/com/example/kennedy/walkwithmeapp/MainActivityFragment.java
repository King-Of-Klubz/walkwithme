package com.example.kennedy.walkwithmeapp;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.SENSOR_SERVICE;


public class MainActivityFragment extends android.support.v4.app.Fragment implements SensorEventListener,StepListener{
    private SensorManager sensorManager;
    private Sensor accel;
    private StepDetector simpleStepDetector;
    private Button btnStartEnd;
    private TextView countStep,distanceTraveled,duration;
    private int numStep,Seconds, Minutes;
    DistanceTraveledService mDistanceTraveledService;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    final Handler handler = new Handler();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        //button and views
        countStep = v.findViewById(R.id.stepCounterTextView);
        btnStartEnd = v.findViewById(R.id.btnActive);
        distanceTraveled = v.findViewById(R.id.distanceTextView);
        duration = v.findViewById(R.id.durationTextView);

        // Get an instance of the sensor manager
        sensorManager= (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        btnStartEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStartEnd.getText()=="FINISH")  {
                    sensorManager.unregisterListener(MainActivityFragment.this);
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);
                    MillisecondTime = 0L ;
                    StartTime = 0L ;
                    TimeBuff = 0L ;
                    UpdateTime = 0L ;
                    Seconds = 0 ;
                    Minutes = 0 ;

                    duration.setText("00:00");
                    btnStartEnd.setText("START");
                    countStep.setText("0");
                }

                else{
                    numStep = 0;
                    sensorManager.registerListener(MainActivityFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    btnStartEnd.setText("FINISH");


                }

            }
        });
        displayDistance();
        return v;
    }

    private void displayDistance() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                double distance = 0;
                if(mDistanceTraveledService != null){
                    distance = mDistanceTraveledService.getDistanceTraveled();
                }
                distanceTraveled.setText(String.valueOf(distance));
                handler.postDelayed(this, 1000);

            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){
            simpleStepDetector.updateAccel(event.timestamp,event.values[0],event.values[1],event.values[2]);
        }
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;

            duration.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) );
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void step(long timeNs) {
        numStep ++;

        countStep.setText(String.valueOf(numStep));
    }
}
