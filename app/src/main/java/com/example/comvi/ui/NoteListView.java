package com.example.comvi.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

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

    /**
     * Constructs a new {@code NoteListView} and sets up the listener
     * for displaying notes when the button is clicked.
     *
     * @param listNotesButton the button that, when clicked, shows the list of notes
     * @param context         the context in which the alert dialog will be displayed
     */
    public NoteListView(Button listNotesButton, Context context) {
        addListener(listNotesButton, context);
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
    public String getNotesInRadiusAsString() {
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
     * @param context         the context used for displaying the alert dialog
     */
    private void addListener(Button listNotesButton, Context context) {
        listNotesButton.setOnClickListener(v -> showNotes(context));
    }

    /**
     * Displays an alert dialog with the notes that are within the radius.
     *
     * @param context the context in which to display the dialog
     */
    public void showNotes(Context context) {
        final String title = "Notes";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(getNotesInRadiusAsString());
        AlertDialog dialog = builder.create();
        addDismissOnTouchListener(dialog);
        dialog.show();
    }

    /**
     * Fügt einem AlertDialog einen OnTouchListener hinzu, der den Dialog schließt,
     * wenn der Benutzer auf die Nachricht innerhalb des Dialogs klickt.
     *
     * @param dialog Der AlertDialog, zu dem der OnTouchListener hinzugefügt wird.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void addDismissOnTouchListener(AlertDialog dialog) {
        dialog.setOnShowListener(dialogInterface -> {
            View messageView = dialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setOnTouchListener((v, event) -> {
                    dialog.dismiss();
                    return true;
                });
            }
        });
    }
}

