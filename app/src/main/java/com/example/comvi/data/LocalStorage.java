package com.example.comvi.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LocalStorage} class provides operations for managing
 * the local storage of notes using SharedPreferences.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class LocalStorage {

    private static final String PREFS_NAME = "MyLocations";
    private static final String KEY_LOCATIONS = "locations";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    /**
     * Constructs a new {@code LocalStorage} instance with the provided context.
     * Initializes SharedPreferences and Gson instances for JSON operations.
     *
     * @param context the context used to access SharedPreferences
     */
    public LocalStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * Deletes the specified note from local storage.
     *
     * @param note the note to be deleted
     */
    @Deprecated
    public void deleteNote(Note note) {
        List<Note> notes = getNotes();
        notes.remove(note);
        String json = gson.toJson(notes);
        sharedPreferences.edit().putString(KEY_LOCATIONS, json).apply();
    }

    /**
     * Saves a new note to local storage by serializing the list of notes
     * to a JSON string and storing it in SharedPreferences.
     *
     * @param note the note to be saved
     */
    public void saveNote(Note note) {
        List<Note> notes = getNotes();
        notes.add(note);
        String json = gson.toJson(notes);
        sharedPreferences.edit().putString(KEY_LOCATIONS, json).apply();
    }

    /**
     * Retrieves all saved notes from local storage by deserializing
     * the JSON string stored in SharedPreferences into a list of notes.
     *
     * @return a list of all saved notes, or an empty list if no notes are found
     */
    public List<Note> getNotes() {
        String json = sharedPreferences.getString(KEY_LOCATIONS, null);
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        List<Note> notes = gson.fromJson(json, type);
        return notes == null ? new ArrayList<>() : notes;
    }

}
