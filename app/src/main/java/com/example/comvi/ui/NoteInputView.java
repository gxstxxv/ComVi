package com.example.comvi.ui;

import android.widget.EditText;

/**
 * The {@code NoteInputView} class manages the note input field,
 * providing methods to retrieve and clear the text content.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class NoteInputView {
    private final EditText editText;

    /**
     * Constructs a new {@code NoteInputView} with the specified {@link EditText}.
     *
     * @param editText the EditText element to be managed
     */
    public NoteInputView(EditText editText) {
        this.editText = editText;
    }

    /**
     * Clears the text content of the associated EditText.
     */
    public void clearEditText() {
        editText.setText("");
    }

    /**
     * Retrieves the current text content from the associated EditText.
     *
     * @return the current text as a String
     */
    public String getNoteText() {
        return editText.getText().toString();
    }
}