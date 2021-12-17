package com.jingyu.otm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jingyu.otm.R;
import com.jingyu.otm.databinding.ActivityRunBinding;
import com.jingyu.otm.db.Run;
import com.jingyu.otm.repository.RunRepository;

public class RunActivity extends AppCompatActivity  {

    ActivityRunBinding binding;
    private double MagnitudePrevious = 0;
    private int seconds = 0;
    private Integer steps = 0;
    private boolean running;
    RunRepository repo;
    private static final String TAG = "RunActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set up the binding
        super.onCreate(savedInstanceState);
        binding = ActivityRunBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        repo = new RunRepository();
        runTimer();

        // set up the sensor
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = true;
            }
        });

        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
            }
        });

        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                seconds = 0;
                steps = 0;
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                Long userId = extras.getLong("userId");
                String input = binding.runName.getText().toString();
                if (input.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "you need to give a run name", Toast.LENGTH_SHORT).show();
                } else {
                    Run run = new Run(userId, input, seconds, steps);
                    Log.d(TAG, "onClick: "+ run.runName);
                    repo.insertRun(run);
                    startActivity(new Intent(RunActivity.this, HomeActivity.class)
                                    .putExtra("userId", userId));
//                    finish();
                }
            }
        });

        // if sensor is null we just give a message
        if (sensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show();
        } else {
            SensorEventListener stepDetector = new SensorEventListener() {

                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if (sensorEvent != null && running) {
                        float x_acceleration = sensorEvent.values[0];
                        float y_acceleration = sensorEvent.values[1];
                        float z_acceleration = sensorEvent.values[2];

                        double Magnitude = Math.sqrt(x_acceleration * x_acceleration
                                + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                        double MagnitudeDelta = Magnitude - MagnitudePrevious;
                        MagnitudePrevious = Magnitude;

                        if (MagnitudeDelta > 6) {
                            steps++;
                        }
                        binding.stepsTaken.setText(steps.toString());
                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };

            sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void runTimer() {
        TextView timeView = binding.timeView;
        Handler handler = new Handler();
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        int hours = seconds/3600;
                        int minutes = (seconds%3600)/60;
                        int secs = seconds % 60;
                        String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                        timeView.setText(time);
                        if (running) {
                            seconds++;
                        }
                        handler.postDelayed(this, 1000);
                    }
                }
        );
    }

}