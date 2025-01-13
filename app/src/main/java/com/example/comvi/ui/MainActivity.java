package com.example.comvi.ui;

import android.hardware.Sensor;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comvi.R;
import com.example.comvi.core.DistanceCalculator;
import com.example.comvi.core.GestureDetector;
import com.example.comvi.core.MotionType;
import com.example.comvi.data.LocalStorage;
import com.example.comvi.data.Note;
import com.example.comvi.location.LocationAPI;
import com.example.comvi.location.LocationService;
import com.example.comvi.sensor.AccelerometerHandler;
import com.example.comvi.sensor.SensorAPI;
import com.example.comvi.util.VibrationManager;

import java.util.List;

/**
 * The {@code MainActivity} class is the main activity of the application.
 * It handles UI initialization, sensor events, location updates, and note management.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements SensorAPI.SensorEventCallback, LocationAPI.LocationResultCallback {

    private AccelerometerHandler accelerometerHandler;
    private GestureDetector gestureDetector;
    private DistanceCalculator distanceCalculator;
    private LocationService locationService;
    private LocalStorage localStorage;
    private VibrationManager vibrationManager;

    private View rootView;
    private MotionFeedbackView motionFeedbackView;
    private NoteInputView noteInputView;
    private NoteListView noteListView;

    private Button listNotesButton;
    private Button requestLocationButton;

    /**
     * Initializes the activity, setting up views and managers.
     *
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeManagers();

        locationService.requestLocation(this);
    }

    /**
     * Initializes the UI components of the activity.
     */
    private void initializeViews() {
        rootView = findViewById(R.id.root_layout);
        motionFeedbackView = findViewById(R.id.motion_feedback_view);
        EditText editText = findViewById(R.id.text_input);
        noteInputView = new NoteInputView(editText);
        rootView.setOnTouchListener(new TouchListener(this, editText));
        listNotesButton = findViewById(R.id.list_notes_button);
        noteListView = new NoteListView(this, listNotesButton);
        requestLocationButton = findViewById(R.id.request_location_button);
    }

    /**
     * Initializes the various managers used in the activity, such as sensors and location services.
     */
    private void initializeManagers() {
        accelerometerHandler = new AccelerometerHandler(this);
        accelerometerHandler.setListener(this);
        vibrationManager = new VibrationManager(this);
        addOnClickRecalibrate(rootView);
        gestureDetector = new GestureDetector();
        distanceCalculator = new DistanceCalculator();
        locationService = new LocationService(this, requestLocationButton);
        localStorage = new LocalStorage(this);
    }

    /**
     * Attaches an OnLongClickListener to the view, triggering a vibration
     * and recalibrating the accelerometer on a long click.
     *
     * @param view the view to which the listener is added
     */
    private void addOnClickRecalibrate(View view) {
        view.setOnLongClickListener(v -> {
            accelerometerHandler.recalibrate();
            vibrationManager.vibrate();
            return true;
        });
    }

    /**
     * Starts listening to sensor and location updates when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        accelerometerHandler.startListening();
        locationService.startLocationUpdates(this);
    }

    /**
     * Stops listening to sensor and location updates when the activity is paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        accelerometerHandler.stopListening();
        locationService.stopLocationUpdates();
    }

    /**
     * Handles accelerometer sensor changes, checking for specific gestures and requests location updates if needed.
     *
     * @param sensor    the sensor that generated the event
     * @param values    the sensor values
     * @param accuracy  the accuracy of the values
     * @param timestamp the time of the event
     */
    @Override
    public void onSensorChanged(Sensor sensor, float[] values, int accuracy, long timestamp) {
        if (motionFeedbackView == null) return;

        motionFeedbackView.updateBallPosition(values);
        MotionType motionType = gestureDetector.detectMotion(values);

        if (motionType == MotionType.DROP && !noteInputView.getNoteText().isEmpty()) {
            motionFeedbackView.displayGestureDetection(rootView);
            vibrationManager.vibrate();
            locationService.requestLocation(this);
        }
    }

    /**
     * Handles location results and saves notes if the location is not null.
     *
     * @param location the current location
     */
    @Override
    public void onLocationResult(Location location) {
        if (location == null) return;

        if (!noteInputView.getNoteText().isEmpty())
            updateLocalStorage(location);

        updateNoteListView(location);
    }

    /**
     * Handles location updates and updates the note list view.
     *
     * @param location the updated location
     */
    @Override
    public void onLocationUpdate(Location location) {
        if (location != null) updateNoteListView(location);
    }

    /**
     * Saves a new note with the current text and specified location to local storage,
     * then clears the input field.
     *
     * @param location the location used to create the note
     */
    private void updateLocalStorage(Location location) {
        Note note = new Note(noteInputView.getNoteText(), location.getLatitude(), location.getLongitude());
        localStorage.saveNote(note);
        noteInputView.clearEditText();
    }

    /**
     * Updates the note list view based on the current location, filtering notes in proximity.
     *
     * @param location the current location
     */
    private void updateNoteListView(Location location) {
        List<Note> notesInRadius = distanceCalculator.getNotesInRadius(location, localStorage.getNotes());
        noteListView.setNotesInRadius(notesInRadius);
        noteListView.updateDialog();
        listNotesButton.setText(String.valueOf(notesInRadius.size()));
    }
}