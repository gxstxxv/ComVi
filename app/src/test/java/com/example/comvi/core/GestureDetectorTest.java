package com.example.comvi.core;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the {@link GestureDetector} class, which detects
 * motions based on accelerometer values.
 *
 * @author gxstxxv
 */
public class GestureDetectorTest {

    private GestureDetector gestureDetector;

    /**
     * Initializes a new {@link GestureDetector} before each test is run.
     */
    @Before
    public void setup() {
        gestureDetector = new GestureDetector();
    }

    /**
     * Tests that no motion is detected when the sensor values remain constantly at zero.
     */
    @Test
    public void testNoMotionDetected() {
        float[] values = new float[]{0.0f, 0.0f, 0.0f};

        for (int i = 0; i < 10; i++) {
            assertEquals(MotionType.NONE, gestureDetector.detectMotion(values));
        }
    }

    /**
     * Tests the detection of a drop motion that meets the detection thresholds.
     */
    @Test
    public void testDropMotionDetected() {
        float[] stableValues = new float[]{0.0f, 0.0f, 5.0f};
        for (int i = 0; i < 5; i++) {
            assertEquals(MotionType.NONE, gestureDetector.detectMotion(stableValues));
        }

        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, 20.0f}));
        assertEquals(MotionType.DROP, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, -15.0f}));
    }

    /**
     * Tests an incomplete drop pattern that does not meet the detection thresholds,
     * resulting in no motion being detected.
     */
    @Test
    public void testIncompleteDropPattern() {
        float[] stableValues = new float[]{0.0f, -7.0f, 5.0f};
        for (int i = 0; i < 5; i++) {
            assertEquals(MotionType.NONE, gestureDetector.detectMotion(stableValues));
        }

        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, 20.0f}));
        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, -5.0f}));
    }

    /**
     * Tests a drop motion where the Y-axis threshold is not breached,
     * thus no drop detection occurs.
     */
    @Test
    public void testDropWithoutYAxisThreshold() {
        float[] stableValues = new float[]{0.0f, -5.0f, 5.0f};
        for (int i = 0; i < 5; i++) {
            assertEquals(MotionType.NONE, gestureDetector.detectMotion(stableValues));
        }

        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -5.0f, 20.0f}));
        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -5.0f, -15.0f}));
    }

    /**
     * Tests the buffer wraparound capability. Verifies that a drop is detected
     * correctly upon complete buffer rotation.
     */
    @Test
    public void testBufferWraparound() {
        float[] stableValues = new float[]{0.0f, -7.0f, 10.0f};
        for (int i = 0; i < 8; i++) {
            assertEquals(MotionType.NONE, gestureDetector.detectMotion(stableValues));
        }

        assertEquals(MotionType.NONE, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, 20.0f}));
        assertEquals(MotionType.DROP, gestureDetector.detectMotion(new float[]{0.0f, -7.0f, -15.0f}));
    }
}
