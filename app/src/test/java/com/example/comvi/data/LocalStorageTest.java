package com.example.comvi.data;

import static com.example.comvi.data.LocalStorage.KEY_LOCATIONS;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link LocalStorage} which verifies the behavior of storing and retrieving notes
 * using {@link SharedPreferences}.
 *
 * @author gxstxxv
 */
@RunWith(MockitoJUnitRunner.class)
public class LocalStorageTest {

    @Mock
    private Context mockContext;

    @Mock
    private SharedPreferences mockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;

    private LocalStorage localStorage;

    /**
     * Sets up the mock environment and initializes the {@link LocalStorage} instance
     * before each test method.
     */
    @Before
    public void setUp() {
        when(mockContext.getSharedPreferences(anyString(), anyInt()))
                .thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        when(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor);

        localStorage = new LocalStorage(mockContext);
    }

    /**
     * Tests that retrieving notes from an empty shared preferences returns an empty list.
     */
    @Test
    public void getNotesWhenEmptyReturnsEmptyList() {
        when(mockSharedPreferences.getString(eq(KEY_LOCATIONS), any()))
                .thenReturn(null);

        List<Note> result = localStorage.getNotes();

        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty", result.isEmpty());
    }

    /**
     * Tests that saving a note into shared preferences stores it correctly by
     * verifying that the editor's putString and apply methods are called.
     */
    @Test
    public void saveNoteStoresNoteInPreferences() {
        Note testNote = new Note("Test content", 12.34, 56.78);
        when(mockSharedPreferences.getString(eq(KEY_LOCATIONS), any()))
                .thenReturn("[]");

        localStorage.saveNote(testNote);

        verify(mockEditor).putString(eq(KEY_LOCATIONS), anyString());
        verify(mockEditor).apply();
    }

    /**
     * Tests that retrieving notes returns the correct list of stored notes,
     * validating both the number of notes and their content.
     */
    @Test
    public void getNotesReturnsStoredNotes() {
        String storedJson = new Gson().toJson(List.of(
                new Note("Note 1", 1.0, 1.0),
                new Note("Note 2", 2.0, 2.0)
        ));
        when(mockSharedPreferences.getString(eq(KEY_LOCATIONS), any()))
                .thenReturn(storedJson);

        List<Note> result = localStorage.getNotes();

        assertNotNull("Result should not be null", result);
        assertEquals("Should return correct number of notes", 2, result.size());
        assertEquals("First note content should match", "Note 1", result.get(0).getContent());
        assertEquals("Second note content should match", "Note 2", result.get(1).getContent());
    }

    /**
     * Tests that saving a new note preserves existing notes in shared preferences.
     * Verifies that the JSON string contains data for both the existing and new notes.
     */
    @Test
    public void saveNotePreservesExistingNotes() {
        Note existingNote = new Note("Existing", 1.0, 1.0);
        String storedJson = new Gson().toJson(List.of(existingNote));
        when(mockSharedPreferences.getString(eq(KEY_LOCATIONS), any()))
                .thenReturn(storedJson);
        Note newNote = new Note("New", 2.0, 2.0);

        localStorage.saveNote(newNote);

        verify(mockEditor).putString(eq(KEY_LOCATIONS), argThat(json ->
                json.contains("Existing") && json.contains("New")
        ));
        verify(mockEditor).apply();
    }
}