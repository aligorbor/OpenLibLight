package ru.geekbrains.android2.openliblight

import android.os.Build
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import ru.geekbrains.android2.openliblight.presenter.book.BookPresenter
import ru.geekbrains.android2.openliblight.view.book.BookActivity
import ru.geekbrains.android2.openliblight.view.book.BookFragment
import ru.geekbrains.android2.openliblight.view.book.ViewBookContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])

class BookPresenterTest {
    private lateinit var presenter: BookPresenter<ViewBookContract>
    private lateinit var scenario: FragmentScenario<BookFragment>

    @Mock
    private lateinit var viewContract: ViewBookContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = BookPresenter()
        scenario =  launchFragmentInContainer()
    }

    @Test
    fun onAttach_View_AssertNotNull() {
        scenario.onFragment {
            presenter.onAttach(it)
            TestCase.assertNotNull(presenter.getView())
        }
    }

    @Test
    fun onDetach_View_AssertNull() {
        scenario.onFragment {
            presenter.onAttach(it)
            presenter.onDetach()
            TestCase.assertNull(presenter.getView())
        }
    }

    @Test
    fun onAttach_MockView_AssertNotNull() {
        presenter.onAttach(viewContract)
        TestCase.assertNotNull(presenter.getView())
    }

    @Test
    fun onDetach_MockView_AssertNull() {
        presenter.onAttach(viewContract)
        presenter.onDetach()
        TestCase.assertNull(presenter.getView())
    }

    @Test
    fun setRaiting_AssertEqual() {
        presenter.setRaiting(TEST_RAITING)
        TestCase.assertEquals(TEST_RAITING, presenter.getRaiting())
    }

    @Test
    fun onIncrement_Test() {
        presenter.onAttach(viewContract)
        presenter.onIncrement()

        Mockito.verify(viewContract, Mockito.times(1)).setRaiting(1)
    }

    @Test
    fun onIncrement_IsWorking() {
        scenario.onFragment {
            presenter.onAttach(it)
            presenter.onIncrement()
            Espresso.onView(ViewMatchers.withId(R.id.tv_raiting))
                .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_PLUS_1)))
        }
    }

    @Test
    fun onDecrement_Test() {
        presenter.onAttach(viewContract)
        presenter.onDecrement()

        Mockito.verify(viewContract, Mockito.times(1)).setRaiting(-1)
    }

    @Test
    fun onDecrement_IsWorking() {
        scenario.onFragment() {
            presenter.onAttach(it)
            presenter.onDecrement()
            Espresso.onView(ViewMatchers.withId(R.id.tv_raiting))
                .check(ViewAssertions.matches(ViewMatchers.withText(TEST_RAITING_MINUS_1)))
        }
    }

}