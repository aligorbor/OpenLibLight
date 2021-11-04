package ru.geekbrains.android2.openliblight

import android.os.Build
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
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
import ru.geekbrains.android2.openliblight.view.book.ViewBookContract

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])

class BookPresenterTest {
    private lateinit var presenter: BookPresenter<ViewBookContract>
    private lateinit var scenario: ActivityScenario<BookActivity>

    @Mock
    private lateinit var viewContract: ViewBookContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = BookPresenter()
        scenario = ActivityScenario.launch(BookActivity::class.java)
    }

    @Test
    fun onAttach_View_AssertNotNull() {
        scenario.onActivity {
            presenter.onAttach(it)
            TestCase.assertNotNull(presenter.getView())
        }
    }

    @Test
    fun onDetach_View_AssertNull() {
        scenario.onActivity {
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
        val raiting = 100
        presenter.setRaiting(raiting)
        TestCase.assertEquals(raiting, presenter.getRaiting())
    }

    @Test
    fun onIncrement_Test() {
        presenter.onAttach(viewContract)
        presenter.onIncrement()

        Mockito.verify(viewContract, Mockito.times(1)).setRaiting(1)
    }

    @Test
    fun onIncrement_IsWorking() {
        scenario.onActivity {
            presenter.onAttach(it)
            presenter.onIncrement()
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertEquals("Raiting: 1", tv.text)
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
        scenario.onActivity {
            presenter.onAttach(it)
            presenter.onDecrement()
            val tv = it.findViewById<TextView>(R.id.tv_raiting)
            TestCase.assertEquals("Raiting: -1", tv.text)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}