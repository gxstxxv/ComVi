package com.example.comvi.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;

/**
 * The {@code MotionFeedbackView} class is a custom view that provides visual feedback
 * for motion events by displaying a movable ball within a confined area.
 * It reacts to sensor data to update the ball's position and offers an animation
 * when a gesture is detected.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class MotionFeedbackView extends View {
    private float ballX;
    private float ballY;
    private float centerX;
    private float centerY;
    private float outerRadius;
    private static final float BALL_RADIUS = 18f;
    private final float ballRadiusDp;
    private final Paint ballPaint;
    private static final float ALPHA_X = 0.005f;
    private static final float ALPHA_Y = 0.025f;
    private float filteredX = 0;
    private float filteredY = 0;
    private static final int normalStrokeWidth = 2;
    private static final int boldStrokeWidth = 4;

    /**
     * Constructs a new {@code MotionFeedbackView} with the specified context and attributes.
     *
     * @param context the context of the view
     * @param attrs   the attribute set to customize the view
     */
    public MotionFeedbackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ballRadiusDp = BALL_RADIUS * context.getResources().getDisplayMetrics().density;

        ballPaint = new Paint();
        ballPaint.setColor(Color.BLACK);
        ballPaint.setStyle(Paint.Style.STROKE);
        ballPaint.setStrokeWidth(normalStrokeWidth);
        ballPaint.setAntiAlias(true);
        ballPaint.setAlpha(220);
    }

    /**
     * Called when the size of the view has changed. Used to initialize the center and radius of
     * the movable area.
     *
     * @param width     the current width of the view
     * @param height    the current height of the view
     * @param oldWidth  the old width of the view
     * @param oldHeight the old height of the view
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        centerX = width / 2f;
        centerY = height / 2f;
        outerRadius = Math.min(width, height) / 1.8f - ballRadiusDp;
        ballX = centerX;
        ballY = centerY;
        filteredX = 0;
        filteredY = 0;
    }

    /**
     * Draws the ball at its current position.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ballX, ballY, ballRadiusDp, ballPaint);
    }

    /**
     * Updates the position of the ball based on the sensor values.
     *
     * @param values the array of sensor values
     */
    public void updateBallPosition(float[] values) {
        float x = -values[0];
        float y = values[1];

        filteredX = ALPHA_X * x + (1 - ALPHA_X) * filteredX;
        filteredY = ALPHA_Y * y + (1 - ALPHA_Y) * filteredY;

        final float MOVEMENT_MULTIPLIER = 1.9f;
        float dx = filteredX * outerRadius / 10 * MOVEMENT_MULTIPLIER;
        float dy = filteredY * outerRadius / 10 * MOVEMENT_MULTIPLIER;

        float newX = centerX + dx;
        float newY = centerY + dy;

        float distanceFromCenter = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
        if (distanceFromCenter > outerRadius - ballRadiusDp) {
            float angle = (float) Math.atan2(newY - centerY, newX - centerX);
            ballX = centerX + (float) Math.cos(angle) * (outerRadius - ballRadiusDp);
            ballY = centerY + (float) Math.sin(angle) * (outerRadius - ballRadiusDp);
            ballPaint.setStrokeWidth(boldStrokeWidth);
        } else {
            ballX = newX;
            ballY = newY;
            ballPaint.setStrokeWidth(normalStrokeWidth);
        }

        invalidate();
    }

    /**
     * Displays a visual indication of gesture detection by animating the background color
     * and stroke of the view.
     *
     * @param rootView the root view of the animation
     */
    public void displayGestureDetection(View rootView) {
        GradientDrawable stroke = (GradientDrawable) this.getBackground();
        stroke.setStroke(boldStrokeWidth, Color.BLACK);

        final String colorString = "#99000000";
        ValueAnimator strokeAnimator = ValueAnimator.ofInt(boldStrokeWidth, normalStrokeWidth);
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), Color.parseColor(colorString), Color.WHITE);

        final long duration = 1000;
        strokeAnimator.setDuration(duration);
        colorAnimator.setDuration(duration);

        strokeAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.setInterpolator(new DecelerateInterpolator());

        strokeAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            stroke.setStroke(animatedValue, Color.BLACK);
        });
        colorAnimator.addUpdateListener(animation -> {
            int animatedColor = (int) animation.getAnimatedValue();
            rootView.setBackgroundColor(animatedColor);
        });

        colorAnimator.start();
        strokeAnimator.start();
    }
}