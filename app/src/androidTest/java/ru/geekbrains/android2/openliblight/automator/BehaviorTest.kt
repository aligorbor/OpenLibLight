package ru.geekbrains.android2.openliblight.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.android2.openliblight.*
import ru.geekbrains.android2.openliblight.TEST_SEARCH_TEXT_FOR_RESULT
import ru.geekbrains.android2.openliblight.TEST_SEARCH_TEXT_FOR_ZERO
import ru.geekbrains.android2.openliblight.TEST_SEARCH_TEXT_RESULT

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {
    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TEST_TIMEOUT)

    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_RESULT
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "resultsCountTextView")),
                TEST_TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), TEST_SEARCH_TEXT_RESULT)
    }

    @Test
    fun test_FabSearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_RESULT
        val btnFab = uiDevice.findObject(By.res(packageName, "search_fab"))
        btnFab.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "resultsCountTextView")),
                TEST_TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), TEST_SEARCH_TEXT_RESULT)
    }

    @Test
    fun test_FabSearchIsNegative() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_ZERO
        val btnFab = uiDevice.findObject(By.res(packageName, "search_fab"))
        btnFab.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "resultsCountTextView")),
                TEST_TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), TEST_SEARCH_TEXT_ZERO)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        uiDevice.wait(
            Until.findObject(By.res(packageName, "resultsCountTextView")),
            TEST_TIMEOUT
        )

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "recyclerView"
            )
        )
        toDetails.click()


        val checkText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "btn_decr")),
                TEST_TIMEOUT
            )
        Assert.assertEquals(checkText.text, TEST_BUTTON_DECR_TEXT)
    }

    @Test
    fun test_OpenDetailsScreen_ShowSearchResult() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_RESULT
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        uiDevice.wait(
            Until.findObject(By.res(packageName, "resultsCountTextView")),
            TEST_TIMEOUT
        )

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "recyclerView"
            )
        )
        toDetails.click()


        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "tv_raiting")),
                TEST_TIMEOUT
            )
        Assert.assertEquals(changedText.text, TEST_SEARCH_TEXT_RESULT_RAITING)
    }

    @Test
    fun test_OpenDetailsScreen_ClickButtonIncr() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_RESULT
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        uiDevice.wait(
            Until.findObject(By.res(packageName, "resultsCountTextView")),
            TEST_TIMEOUT
        )

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "recyclerView"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "tv_raiting")),
                TEST_TIMEOUT
            )
        val btnIncr = uiDevice.findObject(By.res(packageName, "btn_incr"))
        btnIncr.click()

        Assert.assertEquals(changedText.text, TEST_SEARCH_TEXT_RESULT_RAITING_INCR)
    }

    @Test
    fun test_OpenDetailsScreen_ClickButtonDecr() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = TEST_SEARCH_TEXT_FOR_RESULT
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        uiDevice.wait(
            Until.findObject(By.res(packageName, "resultsCountTextView")),
            TEST_TIMEOUT
        )

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "recyclerView"
            )
        )
        toDetails.click()

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "tv_raiting")),
                TEST_TIMEOUT
            )
        val btnIncr = uiDevice.findObject(By.res(packageName, "btn_decr"))
        btnIncr.click()

        Assert.assertEquals(changedText.text, TEST_SEARCH_TEXT_RESULT_RAITING_DECR)
    }

}