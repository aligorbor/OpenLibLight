package ru.geekbrains.android2.openliblight.espresso

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.android2.openliblight.*
import ru.geekbrains.android2.openliblight.view.book.BookFragment

@RunWith(AndroidJUnit4::class)
class BookFragmentEspressoTest {
    private lateinit var scenario: FragmentScenario<BookFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragment_testBundle() {
        val scenario = launchFragmentInContainer<BookFragment>(ARG_BUNDLE)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val assertion =
            ViewAssertions.matches(ViewMatchers.withText(TEST_SEARCH_TEXT_RESULT_RAITING))
        onView(withId(R.id.tv_raiting)).check(assertion)
    }

    @Test
    fun fragment_testSetRaitingMethod() {
        scenario.onFragment { fragment ->
            fragment.setRaiting(TEST_RAITING)
        }
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_HUNDRED))
        onView(withId(R.id.tv_raiting)).check(assertion)
    }

    @Test
    fun fragment_AssertNotNull() {
        scenario.onFragment {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun fragmentTextView_Raiting_HasText() {
        onView(withId(R.id.tv_raiting))
            .check((ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_ZERO))))
    }

    @Test
    fun fragmentTextView_Title_HasText() {
        onView(withId(R.id.tv_title))
            .check((ViewAssertions.matches(ViewMatchers.withText(TEST_TITLE_BLANK))))
    }

    @Test
    fun fragmentTextView_Authors_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_AUTHOR_BLANK))
        onView(withId(R.id.tv_authors)).check(assertion)
    }

    @Test
    fun fragmentTextView_Raiting_IsDisplayed() {
        onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun fragmentTextView_Title_IsDisplayed() {
        onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun fragmentTextView_Authors_IsDisplayed() {
        onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun fragmentImageView_Cover_IsDisplayed() {
        onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun fragmentTextView_Raiting_IsCompletelyDisplayed() {
        onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun fragmentTextView_Title_IsCompletelyDisplayed() {
        onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun fragmentTextView_Authors_IsCompletelyDisplayed() {
        onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun fragmentImageView_Cover_IsCompletelyDisplayed() {
        onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun fragmentViews_AreEffectiveVisible() {
        onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    @Test
    fun fragmentButtons_AreEffectiveVisible() {
        onView(withId(R.id.btn_incr))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.btn_decr))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun fragmentButtonIncrement_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_BUTTON_INCR_TEXT))
        onView(withId(R.id.btn_incr)).check(assertion)
    }

    @Test
    fun fragmentButtonDecrement_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_BUTTON_DECR_TEXT))
        onView(withId(R.id.btn_decr)).check(assertion)
    }

    @Test
    fun fragmentButtonIncrement_IsWorking() {
        onView(withId(R.id.btn_incr)).perform(ViewActions.click())
        onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_PLUS_1)))
    }

    @Test
    fun fragmentButtonDecrement_IsWorking() {
        onView(withId(R.id.btn_decr)).perform(ViewActions.click())
        onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_MINUS_1)))
    }
}