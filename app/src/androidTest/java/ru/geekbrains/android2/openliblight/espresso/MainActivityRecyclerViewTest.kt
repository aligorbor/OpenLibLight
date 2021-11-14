package ru.geekbrains.android2.openliblight.espresso

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.android2.openliblight.*
import ru.geekbrains.android2.openliblight.view.books.BooksAdapter
import ru.geekbrains.android2.openliblight.view.books.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_ScrollTo() {
        loadList()
        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollTo<BooksAdapter.SearchResultViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(TEST_RECYCLE_SCROLL_TO))
                )
            )
    }

    @Test
    fun activitySearch_PerformClickAtPosition() {
        loadList()

        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BooksAdapter.SearchResultViewHolder>(
                    TEST_RECYCLE_CLICK_AT,
                    ViewActions.click()
                )
            )
    }

    @Test
    fun activitySearch_PerformClickOnItem() {
        loadList()

        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollTo<BooksAdapter.SearchResultViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(TEST_RECYCLE_SCROLL_TO))
                )
            )

        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<BooksAdapter.SearchResultViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(TEST_RECYCLE_CLICK_ON)),
                    ViewActions.click()
                )
            )

    }

    @Test
    fun activitySearch_PerformCustomClick() {
        loadList()

        Espresso.onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<BooksAdapter.SearchResultViewHolder>(
                        TEST_RECYCLE_CLICK_AT,
                        tapOnItemWithId(R.id.checkFavorite)
                    )
            )
    }

    private fun loadList() {
        Espresso.onView(withId(R.id.searchEditText)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.searchEditText)).perform(
            ViewActions.replaceText(TEST_SEARCH_TEXT_FOR_RESULT),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
        Espresso.onView(ViewMatchers.isRoot()).perform(delay())
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = TEST_DELAY_DESCR
            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(TEST_DELAY)
            }
        }
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }

        override fun getDescription(): String {
            return TAP_ON_ITEM_WITH_ID_DESCRIPTION
        }

        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as View
            v.performClick()
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}