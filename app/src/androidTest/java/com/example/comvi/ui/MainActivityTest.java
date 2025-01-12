package com.example.comvi.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.comvi.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for {@link MainActivity} using Espresso to verify UI interactions and states.
 *
 * @author gxstxxv
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    /**
     * Launches {@link MainActivity} for each test method in the class.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Verifies that the note input field is properly displayed, enabled, accepts
     * text input, and retains the entered text after closing the soft keyboard.
     */
    @Test
    public void testNoteInputAttributes() {
        onView(withId(R.id.textInput))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        String testNote = "Line 1\nLine 2\nLine 3";
        onView(withId(R.id.textInput))
                .perform(typeText(testNote), closeSoftKeyboard());

        onView(withId(R.id.textInput))
                .check(matches(withText(testNote)));
    }

    /**
     * Checks that the motion feedback view is visible on the screen.
     */
    @Test
    public void testMotionFeedbackViewLayout() {
        onView(withId(R.id.motionFeedbackView))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies that the "list notes" button is displayed on the screen,
     * ensuring its presence in the layout.
     */
    @Test
    public void testListNotesButtonLayout() {
        onView(withId(R.id.list_notes_button))
                .check(matches(isDisplayed()));
    }

    /**
     * Tests that clicking the list notes button displays a dialog with the correct title and
     * that the dialog can be closed by clicking the close button.
     */
    @Test
    public void testDialogTitleAndClose() {
        onView(withId(R.id.list_notes_button))
                .perform(click());
        onView(withId(R.id.dialogTitle))
                .check(matches(isDisplayed()))
                .check(matches(withText("Notes")));
        onView(withId(R.id.closeButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.closeButton))
                .perform(click());
    }

    /**
     * Ensures that the dialog displayed by clicking the list notes button shows an empty
     * message area, and that it can be closed with the provided close button.
     */
    @Test
    public void testEmptyDialogState() {
        onView(withId(R.id.list_notes_button))
                .perform(click());
        onView(withId(android.R.id.message))
                .check(matches(withText("")));
        onView(withId(R.id.closeButton))
                .perform(click());
    }

    /**
     * Checks that the root layout is enabled and can retain focus after interacting with
     * other components, specifically verifying that typed input is retained after focusing
     * back to the root layout.
     */
    @Test
    public void testRootLayoutFocusability() {
        onView(withId(R.id.root_layout))
                .check(matches(isEnabled()));

        String testInput = "Test input";
        onView(withId(R.id.textInput))
                .perform(typeText(testInput), closeSoftKeyboard());

        onView(withId(R.id.root_layout))
                .perform(click());
        onView(withId(R.id.textInput))
                .check(matches(withText(testInput)));
    }

}
