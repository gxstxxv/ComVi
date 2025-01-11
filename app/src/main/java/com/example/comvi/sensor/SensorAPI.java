package com.example.comvi.sensor;

import android.hardware.Sensor;

/**
 * The {@code SensorAPI} interface defines operations for managing
 * sensor-related activities and events.
 *
 * @author gxstxxv
 * @version 1.0
 */
public interface SensorAPI {

    /**
     * The {@code SensorEventCallback} interface provides callback methods
     * for handling sensor events.
     */
    interface SensorEventCallback {

        /**
         * Called when there is a new sensor event.
         *
         * @param sensor    the sensor that generated the event
         * @param values    the values from the sensor event
         * @param accuracy  the accuracy of the sensor event
         * @param timestamp the timestamp when the event occurred
         */
        void onSensorChanged(Sensor sensor, float[] values, int accuracy, long timestamp);
    }

}