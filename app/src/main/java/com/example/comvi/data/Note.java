package com.example.comvi.data;

import androidx.annotation.NonNull;

/**
 * The {@code Note} class represents a note that includes textual content
 * and geolocation information specified by latitude and longitude.
 */
public class Note {

    private final String content;
    private final double latitude;
    private final double longitude;

    /**
     * Constructs a new {@code Note} with the specified content and location coordinates.
     *
     * @param content   the textual content of the note
     * @param latitude  the latitude where the note is associated
     * @param longitude the longitude where the note is associated
     */
    public Note(String content, double latitude, double longitude) {
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the content of the note.
     *
     * @return the textual content of the note
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the latitude of the note's location.
     *
     * @return the latitude coordinate
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the note's location.
     *
     * @return the longitude coordinate
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns a string representation of the note, including its content
     * and location coordinates.
     *
     * @return a string representation of the note
     */
    @NonNull
    @Override
    public String toString() {
        return "Note: " + content + " Longitude: " + longitude + " Latitude: " + latitude;
    }

}