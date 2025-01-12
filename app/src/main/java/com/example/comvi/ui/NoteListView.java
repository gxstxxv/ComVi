package com.example.comvi.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.comvi.R;
import com.example.comvi.data.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code NoteListView} class manages the display and interaction
 * of notes that fall within a specified radius, providing a visual list
 * when triggered.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class NoteListView {

    private List<Note> notesInRadius = new ArrayList<>();
    private final Context context;
    private AlertDialog dialog;

    /**
     * Constructs a new {@code NoteListView} and sets up the listener
     * for displaying notes when the button is clicked.
     *
     * @param context         the context in which the alert dialog will be displayed
     * @param listNotesButton the button that, when clicked, shows the list of notes
     */
    public NoteListView(Context context, Button listNotesButton) {
        this.context = context;
        initDialog();
        addListener(listNotesButton);
    }

    /**
     * Initializes the alert dialog used to display notes.
     * The dialog includes a custom title layout with a close button.
     */
    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View dialogWithCloseButton = inflater.inflate(R.layout.dialog_with_close_button, null);
        builder.setCustomTitle(dialogWithCloseButton);

        dialog = builder.create();
        ImageView closeButton = dialogWithCloseButton.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> dialog.dismiss());
    }

    /**
     * TODO: test
     * Updates the content of the alert dialog by setting its message to a
     * formatted string containing the notes currently within the radius.
     */
    public void updateDialog() {
        dialog.setMessage(getNotesInRadiusAsString());
    }

    /**
     * Updates the list of notes that are within the specified radius.
     *
     * @param notesInRadius a list of notes that are within the radius
     */
    public void setNotesInRadius(List<Note> notesInRadius) {
        this.notesInRadius = notesInRadius;
    }

    /**
     * Retrieves the contents of the notes within the radius as a single string.
     *
     * @return a string combining all notes' content, each separated by a newline
     */
    private String getNotesInRadiusAsString() {
        StringBuilder notes = new StringBuilder();
        for (Note note : notesInRadius) {
            notes.append(note.getContent()).append(System.lineSeparator());
        }
        return notes.toString();
    }

    /**
     * Adds a click listener to the list notes button to trigger the display
     * of notes within the radius.
     *
     * @param listNotesButton the button to which the listener is added
     */
    private void addListener(Button listNotesButton) {
        listNotesButton.setOnClickListener(v -> showNotes());
    }

    /**
     * Displays an alert dialog with the notes that are within the radius.
     */
    public void showNotes() {
        dialog.show();
    }

}

