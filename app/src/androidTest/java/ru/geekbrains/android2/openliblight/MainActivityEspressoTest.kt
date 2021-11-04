package ru.geekbrains.android2.openliblight

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.android2.openliblight.view.books.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityEditText_NotNull() {
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            TestCase.assertNotNull(searchEditText)
        }
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val resultsCountTextView = it.findViewById<TextView>(R.id.resultsCountTextView)
            TestCase.assertNotNull(resultsCountTextView)
        }
    }

    @Test
    fun activityEditText_HasText() {
        val assertion = matches(withText("cat"))
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(
            replaceText("cat"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.searchEditText)).check(assertion)
    }

    @Test
    fun activityEditText_IsDisplayed() {
        onView(withId(R.id.searchEditText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun activityEditText_IsCompletelyDisplayed() {
        onView(withId(R.id.searchEditText))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityEditText_IsEffectiveVisible() {
        onView(withId(R.id.searchEditText))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activityTextView_IsEffectiveInVisible() {
        onView(withId(R.id.resultsCountTextView))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(
            replaceText("dog"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
        onView(isRoot()).perform(delay())
        onView(withId(R.id.resultsCountTextView)).check(
            matches(
                withText("Number of results: 49")
            )
        )
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $3 seconds"
            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(3000)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}