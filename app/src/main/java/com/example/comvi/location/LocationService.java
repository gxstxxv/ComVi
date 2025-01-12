package com.example.comvi.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationRequest.Builder;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

/**
 * The {@code LocationService} class implements the {@link LocationAPI} interface
 * to provide location services using the Fused Location Provider API or the
 * Android LocationManager as a fallback.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class LocationService implements LocationAPI {

    private final Context context;
    private final FusedLocationProviderClient fusedLocationClient;
    private final LocationManager locationManager;
    private LocationListener locationListener;

    /**
     * Constructs a new {@code LocationService} with the provided context.
     *
     * @param context               the context used to access location services and permissions
     * @param requestLocationButton the button that, when clicked, requests the location
     */
    public LocationService(Context context, Button requestLocationButton) {
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        addListener(requestLocationButton);
    }

    /**
     * Adds a click listener to the request location button to trigger the request location method.
     *
     * @param requestLocationButton the button to which the listener is added
     */
    private void addListener(Button requestLocationButton) {
        requestLocationButton.setOnClickListener(v -> requestLocation((LocationResultCallback) context));
    }

    /**
     * Requests the current location using the Fused Location Provider.
     *
     * @param callback the callback to receive the location result
     */
    @Override
    public void requestLocation(LocationResultCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        final long intervalMillis = 10000;
        final long minUpdateIntervalMillis = 5000;
        final int maxUpdates = 1;
        Builder builder = new Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalMillis).setMinUpdateIntervalMillis(minUpdateIntervalMillis).setMaxUpdates(maxUpdates);
        LocationRequest locationRequest = builder.build();

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    callback.onLocationResult(location);
                }
                fusedLocationClient.removeLocationUpdates(this);
            }
        }, null);
    }

    /**
     * Starts continuous location updates using the LocationManager.
     *
     * @param callback the callback to receive location updates
     */
    @Override
    public void startLocationUpdates(LocationResultCallback callback) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                callback.onLocationUpdate(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }
        };

        /*
        minTimeMs = 10000;
        minDistanceM = 20;
         */
        final long minTimeMs = 5000;
        final float minDistanceM = 10;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTimeMs, minDistanceM, locationListener);
    }

    /**
     * Stops location updates if a listener has been registered.
     */
    public void stopLocationUpdates() {
        if (locationListener != null) locationManager.removeUpdates(locationListener);
    }

}