package com.example.comvi.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * The {@code TouchListener} class implements a custom touch listener
 * for a {@link View}, specifically handling touch events to manage
 * the focus of an {@link EditText} and the visibility of the soft keyboard.
 *
 * @author gxstxxv
 * @version 1.0
 */
public class TouchListener implements View.OnTouchListener {
    private final EditText editText;
    private final Context context;

    /**
     * Constructs a new {@code TouchListener} with the specified
     * context and edit text.
     *
     * @param context  the context to be used for accessing system services
     * @param editText the {@link EditText} whose focus will be managed
     */
    public TouchListener(Context context, EditText editText) {
        this.editText = editText;
        this.context = context;
    }

    /**
     * Handles touch events on the associated view. If the {@link EditText}
     * is focused, it clears the focus and hides the soft keyboard.
     *
     * @param v     the view that received the touch event
     * @param event the motion event describing the touch
     * @return false to indicate that the touch event is not consumed and should
     * be handled by the next listener if available
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (editText.isFocused()) {
            editText.clearFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
        return false;
    }
}