package com.example.comvi.location;

import android.location.Location;

/**
 * The {@code LocationAPI} interface defines operations for managing
 * location-related activities, including requesting the current location
 * and receiving location updates.
 *
 * @author gxstxxv
 * @version 1.0
 */
public interface LocationAPI {

    /**
     * The {@code LocationResultCallback} interface provides callback methods
     * for receiving location results and updates.
     */
    interface LocationResultCallback {

        /**
         * Called when the current location is obtained.
         *
         * @param location the current location
         */
        void onLocationResult(Location location);

        /**
         * Called when the location is updated.
         *
         * @param location the updated location
         */
        void onLocationUpdate(Location location);

    }

    /**
     * Requests the current location and provides it via the specified callback.
     *
     * @param callback the callback to receive the location result
     */
    void requestLocation(LocationResultCallback callback);

    /**
     * Initiates continuous location updates and provides them via the specified callback.
     *
     * @param callback the callback to receive location updates
     */
    void startLocationUpdates(LocationResultCallback callback);

}