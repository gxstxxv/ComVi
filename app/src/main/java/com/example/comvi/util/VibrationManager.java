package com.example.comvi.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

/**
 * The {@code VibrationManager} class provides a simple API for
 * managing vibration effects on a device.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class VibrationManager {
    private final Vibrator vibrator;

    /**
     * Constructs a new {@code VibrationManager} using the provided context.
     * It retrieves the system's vibrator service.
     *
     * @param context the context used to access the vibrator service
     */
    public VibrationManager(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Triggers a vibration on the device. The vibration duration is 500 milliseconds.
     * Uses different APIs depending on the Android version.
     */
    public void vibrate() {
        final long milliseconds = 500;
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            else vibrator.vibrate(milliseconds);
        }
    }
}