package com.example.comvi.core;

import android.location.Location;

import com.example.comvi.data.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DistanceCalculator} class provides functionality
 * to filter notes based on their proximity to a given location.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class DistanceCalculator {

    private static final double OFFSET = 0.0004;

    /**
     * Filters and returns a list of notes that are within a specific
     * offset distance from the provided location.
     *
     * @param location the reference location from which distances are measured
     * @param notes    the list of notes to be evaluated
     * @return a list of notes that are within the specified offset distance from the given location
     */
    public List<Note> getNotesInRadius(Location location, List<Note> notes) {
        List<Note> notesInRadius = new ArrayList<>();
        for (Note note : notes) {
            if ((Math.abs(note.getLongitude() - location.getLongitude()) <= OFFSET) || (Math.abs(note.getLatitude() - location.getLatitude()) <= OFFSET))
                notesInRadius.add(note);
        }
        return notesInRadius;
    }

}
