package com.example.comvi.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the {@link Note} class, verifying its creation,
 * getter methods, and string representation functionality.
 *
 * @author gxstxxv
 */
public class NoteTest {

    /**
     * Tests the creation of a {@link Note} object and verifies that
     * its getter methods return the expected values for content,
     * latitude, and longitude.
     */
    @Test
    public void testNoteCreationAndGetters() {
        String content = "Test Content";
        double latitude = 1.234;
        double longitude = 5.678;

        Note note = new Note(content, latitude, longitude);

        assertEquals(content, note.getContent());
        assertEquals(latitude, note.getLatitude(), 0.0001);
        assertEquals(longitude, note.getLongitude(), 0.0001);
    }

    /**
     * Tests the string representation of a {@link Note} object to ensure
     * it includes the note's content and coordinate values.
     */
    @Test
    public void testNoteToString() {
        Note note = new Note("Test", 1.0, 1.0);
        String toString = note.toString();

        assertTrue(toString.contains("Test"));
        assertTrue(toString.contains("1.0"));
    }

}