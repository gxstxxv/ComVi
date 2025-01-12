package com.example.comvi.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import android.location.Location;

import com.example.comvi.data.Note;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test class for testing the functionality of the DistanceCalculator.
 *
 * @author gxstxxv
 */
public class DistanceCalculatorTest {

    private final DistanceCalculator distanceCalculator = new DistanceCalculator();
    @Mock
    private Location mockLocation;

    /**
     * Sets up the mock objects and initializes dependencies before each test case.
     */
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getNotesInRadius() method to ensure it correctly identifies notes within a specified radius.
     * Scenario: Given a mock location at (0.0, 0.0), it verifies that the note within radius
     * is included in the result, and the note outside the radius is excluded.
     */
    @Test
    public void testNotesInRadius() {
        when(mockLocation.getLatitude()).thenReturn(0.0);
        when(mockLocation.getLongitude()).thenReturn(0.0);

        Note noteInRadius = new Note("Test note 1", 0.0003, 0.0003);
        Note noteOutOfRadius = new Note("Test note 2", 0.001, 0.001);
        List<Note> allNotes = Arrays.asList(noteInRadius, noteOutOfRadius);

        List<Note> notesInRadius = distanceCalculator.getNotesInRadius(mockLocation, allNotes);

        assertEquals(1, notesInRadius.size());
        assertTrue(notesInRadius.contains(noteInRadius));
        assertFalse(notesInRadius.contains(noteOutOfRadius));
    }

    /**
     * Tests the getNotesInRadius() method for handling edge cases around the boundary of the radius.
     * Scenario: Given a mock location at (1.0, 1.0), it evaluates the notes that lie inside, outside,
     * and on the boundary latitude, ensuring accurate inclusion or exclusion by the system.
     */
    @Test
    public void testEdgeCasesInRadius() {
        when(mockLocation.getLatitude()).thenReturn(1.0);
        when(mockLocation.getLongitude()).thenReturn(1.0);

        Note noteInRadius = new Note("Inside", 1.0003, 1.0003);
        Note noteOutOfRadius = new Note("Outside", 1.0005, 1.0005);
        Note noteBoundaryLatitude = new Note("Boundary Lat", 1.0004, 1.0003);

        List<Note> testNotes = Arrays.asList(noteInRadius, noteOutOfRadius, noteBoundaryLatitude);
        List<Note> notesInRadius = distanceCalculator.getNotesInRadius(mockLocation, testNotes);

        assertEquals(2, notesInRadius.size());
        assertTrue(notesInRadius.contains(noteInRadius));
        assertTrue(notesInRadius.contains(noteBoundaryLatitude));
        assertFalse(notesInRadius.contains(noteOutOfRadius));
    }

    /**
     * Tests the getNotesInRadius() method when given an empty list of notes.
     * Scenario: Given a mock location, it verifies that the result is an empty list,
     * confirming that the method handles empty inputs without error.
     */
    @Test
    public void testEmptyCase() {
        when(mockLocation.getLatitude()).thenReturn(0.0);
        when(mockLocation.getLongitude()).thenReturn(0.0);

        List<Note> emptyList = new ArrayList<>();
        List<Note> resultEmpty = distanceCalculator.getNotesInRadius(mockLocation, emptyList);
        assertTrue(resultEmpty.isEmpty());
    }

}