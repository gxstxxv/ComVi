package com.example.comvi.core;

/**
 * The {@code GestureDetector} class is responsible for detecting drop motions
 * using accelerometer data.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class GestureDetector {

    private static final float Z_RISE_THRESHOLD = 15.0f;
    private static final float Z_FALL_THRESHOLD = -10.0f;
    private static final float Y_FALL_THRESHOLD = -6.0f;

    private static final int BUFFER_SIZE = 10;
    private final float[] zBuffer = new float[BUFFER_SIZE];
    private final float[] yBuffer = new float[BUFFER_SIZE];
    private int bufferIndex = 0;

    /**
     * Detects the type of motion based on the given accelerometer values.
     *
     * @param values the accelerometer values, where values[0] is the X-axis, values[1] the Y-axis and values[2] the Z-axis
     * @return the detected motion type, either {@code MotionType.DROP} if a drop is detected,
     * or {@code MotionType.NONE} if no significant motion is detected
     */
    public MotionType detectMotion(float[] values) {
        float y = values[1];
        float z = values[2];

        zBuffer[bufferIndex] = z;
        yBuffer[bufferIndex] = y;
        bufferIndex = (bufferIndex + 1) % BUFFER_SIZE;

        for (int i = 0; i < BUFFER_SIZE - 2; i++) {
            if (zBuffer[i] < Z_RISE_THRESHOLD && zBuffer[i + 1] > Z_RISE_THRESHOLD && zBuffer[i + 2] < Z_FALL_THRESHOLD && yBuffer[i + 1] < Y_FALL_THRESHOLD)
                return MotionType.DROP;
        }

        return MotionType.NONE;
    }

}
