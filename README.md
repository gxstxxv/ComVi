# ComVi - Context-Aware Mobile Note Taking

ComVi is an Android application that enables location-based note taking through an innovative gesture-based interface. The app allows users to create and access notes tied to specific geographical locations, making it ideal for context-aware information retrieval.

## Features

- **Gesture-Based Note Creation**: Drop notes using intuitive physical gestures detected by the device's accelerometer
- **Location-Aware Storage**: Automatically associates notes with the user's current geographical location
- **Proximity-Based Retrieval**: Access notes based on physical proximity to their creation location
- **Visual Motion Feedback**: Real-time visual feedback through an interactive ball display that responds to device movement
- **Haptic Feedback**: Vibration confirmation when gestures are successfully detected

## Technical Implementation

The application is built using modern Android development practices and includes:

- Custom view implementations for motion feedback and note management
- Accelerometer-based gesture detection system
- Integration with Google's Fused Location Provider API
- Local data persistence using SharedPreferences with JSON serialization
- Comprehensive error handling and permission management

## Requirements

- Android SDK version compatible with modern Android devices
- Location services enabled on the device
- Accelerometer sensor
- ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the application on your Android device
4. Grant the required location permissions when prompted
5. Calibrate the motion sensor by long-pressing anywhere on the screen

## Usage

1. Enter your note text in the input field
2. Perform a dropping gesture with your device to save the note at your current location
3. View nearby notes by tapping the list button
4. Long-press anywhere on the screen to recalibrate the motion sensor
