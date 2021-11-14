package ru.geekbrains.android2.openliblight.espresso

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.android2.openliblight.*
import ru.geekbrains.android2.openliblight.TEST_AUTHOR_BLANK
import ru.geekbrains.android2.openliblight.TEST_BUTTON_DECR_TEXT
import ru.geekbrains.android2.openliblight.TEST_BUTTON_INCR_TEXT
import ru.geekbrains.android2.openliblight.TEST_RAITING_MINUS_1
import ru.geekbrains.android2.openliblight.TEST_RAITING_PLUS_1
import ru.geekbrains.android2.openliblight.TEST_RAITING_ZERO
import ru.geekbrains.android2.openliblight.TEST_TITLE_BLANK
import ru.geekbrains.android2.openliblight.view.book.BookActivity

@RunWith(AndroidJUnit4::class)
class BookActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<BookActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(BookActivity::class.java)
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
    fun activityTextView_Raiting_NotNull() {
        scenario.onActivity {
            val tv_raiting = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertNotNull(tv_raiting)
        }
    }

    @Test
    fun activityTextView_Title_NotNull() {
        scenario.onActivity {
            val tv_title = it.findViewById<TextView>(R.id.tv_title)
            TestCase.assertNotNull(tv_title)
        }
    }

    @Test
    fun activityTextView_Authors_NotNull() {
        scenario.onActivity {
            val tv_authors = it.findViewById<TextView>(R.id.tv_authors)
            TestCase.assertNotNull(tv_authors)
        }
    }

    @Test
    fun activityImageView_Cover_NotNull() {
        scenario.onActivity {
            val iv_cover = it.findViewById<ImageView>(R.id.iv_cover)
            TestCase.assertNotNull(iv_cover)
        }
    }

    @Test
    fun activityTextView_Raiting_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_ZERO))
        Espresso.onView(withId(R.id.tv_raiting)).check(assertion)
    }

    @Test
    fun activityTextView_Title_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_TITLE_BLANK))
        Espresso.onView(withId(R.id.tv_title)).check(assertion)
    }

    @Test
    fun activityTextView_Authors_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_AUTHOR_BLANK))
        Espresso.onView(withId(R.id.tv_authors)).check(assertion)
    }

    @Test
    fun activityTextView_Raiting_IsDisplayed() {
        Espresso.onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityTextView_Title_IsDisplayed() {
        Espresso.onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityTextView_Authors_IsDisplayed() {
        Espresso.onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityImageView_Cover_IsDisplayed() {
        Espresso.onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityTextView_Raiting_IsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun activityTextView_Title_IsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun activityTextView_Authors_IsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun activityImageView_Cover_IsCompletelyDisplayed() {
        Espresso.onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
    }

    @Test
    fun activityViews_AreEffectiveVisible() {
        Espresso.onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.tv_title))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.tv_authors))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.iv_cover))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }

    @Test
    fun activityButtons_AreEffectiveVisible() {
        Espresso.onView(withId(R.id.btn_incr))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        Espresso.onView(withId(R.id.btn_decr))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun activityButtonIncrement_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_BUTTON_INCR_TEXT))
        Espresso.onView(withId(R.id.btn_incr)).check(assertion)
    }

    @Test
    fun activityButtonDecrement_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(TEST_BUTTON_DECR_TEXT))
        Espresso.onView(withId(R.id.btn_decr)).check(assertion)
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        Espresso.onView(withId(R.id.btn_incr)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_PLUS_1)))
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        Espresso.onView(withId(R.id.btn_decr)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.tv_raiting))
            .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_MINUS_1)))
    }

    @After
    fun close() {
        scenario.close()
    }
}