package com.example.comvi.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * The {@code AccelerometerHandler} class handles events related to the accelerometer sensor.
 * It implements calibration and provides calibrated sensor events to a listener.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class AccelerometerHandler implements SensorEventListener {

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private SensorAPI.SensorEventCallback listener;

    private final float[] offset = new float[3];
    private boolean isCalibrated;

    /**
     * Constructs a new {@code AccelerometerHandler} with the specified context, initializing
     * the sensor manager and retrieving the default accelerometer sensor.
     *
     * @param context the context used to access system services
     */
    public AccelerometerHandler(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager != null ? sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) : null;
    }

    /**
     * Sets the listener for receiving sensor events.
     *
     * @param listener the listener that handles sensor events
     */
    public void setListener(SensorAPI.SensorEventCallback listener) {
        this.listener = listener;
    }

    /**
     * Starts listening to the accelerometer sensor with the fastest delay.
     */
    public void startListening() {
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
        isCalibrated = false;
    }

    /**
     * Stops listening to the accelerometer sensor.
     */
    public void stopListening() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    /**
     * Sets the initial acceleration values to establish a baseline for calibration.
     * The initial values are stored in the offset array.
     *
     * @param values the initial accelerometer values to be stored
     */
    private void setInitialValues(float[] values) {
        System.arraycopy(values, 0, offset, 0, values.length);
    }

    /**
     * Resets the calibration status so that the next sensor event will recalibrate the sensor.
     */
    public void recalibrate() {
        isCalibrated = false;
    }

    /**
     * TODO: test
     * Calibrates the given accelerometer values by subtracting the offset values.
     * This helps in normalizing the sensor data based on the initial baseline.
     *
     * @param values the raw accelerometer values to be calibrated
     * @return an array of calibrated values
     */
    private float[] calibrate(float[] values) {
        float[] calibratedValues = new float[3];
        for (int i = 0; i < calibratedValues.length; i++) {
            calibratedValues[i] = values[i] - offset[i];
        }
        return calibratedValues;
    }

    /**
     * Handles sensor change events by calibrating the values and passing them to the listener.
     * Calibrates the sensor values based on an offset initialized at the start.
     *
     * @param event the sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && listener != null) {
            if (!isCalibrated) {
                setInitialValues(event.values);
                isCalibrated = true;
            }
            listener.onSensorChanged(event.sensor, calibrate(event.values), event.accuracy, event.timestamp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this implementation
    }
}