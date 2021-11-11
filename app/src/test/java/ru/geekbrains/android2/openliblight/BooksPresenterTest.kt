package ru.geekbrains.android2.openliblight

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.Work
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.books.BooksPresenter
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.books.MainActivity
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class BooksPresenterTest {
    private lateinit var presenter: BooksPresenter<ViewBooksContract>
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Mock
    private lateinit var repository: RepositoryContract

    @Mock
    private lateinit var viewContract: ViewBooksContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = BooksPresenter(repository)
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun onAttach_View_AssertNotNull() {
        scenario.onActivity {
            presenter.onAttach(it)
            TestCase.assertNotNull(presenter.getView())
        }
    }

    @Test
    fun onAttach_MockView_AssertNotNull() {
        presenter.onAttach(viewContract)
        TestCase.assertNotNull(presenter.getView())
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
    fun onDetach_MockView_AssertNull() {
        presenter.onAttach(viewContract)
        presenter.onDetach()
        TestCase.assertNull(presenter.getView())
    }

    @Test
    fun searchOpenLib_Test() {
        presenter.searchOpenLib(TEST_SEARCH_QUERY)
        Mockito.verify(repository, Mockito.times(1)).searchOpenLib(TEST_SEARCH_QUERY, presenter)
    }

    @Test
    fun handleOpenLibError_Test() {
        presenter.onAttach(viewContract)
        presenter.handleOpenLibError()
        Mockito.verify(viewContract, Mockito.times(1)).displayError()
    }

    @Test
    fun handleOpenLibResponse_ResponseUnsuccessful() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Assert.assertFalse(response.isSuccessful)
    }

    @Test
    fun handleOpenLibResponse_Failure() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        presenter.onAttach(viewContract)
        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError(TEST_DISPLAY_ERROR_UNSUCCESSFUL)
    }

    @Test
    fun handleOpenLibResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)

        presenter.onAttach(viewContract)
        presenter.handleOpenLibResponse(response)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError(TEST_DISPLAY_ERROR_UNSUCCESSFUL)
    }

    @Test
    fun handleOpenLibResponse_ResponseIsEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.body()).thenReturn(null)

        presenter.handleOpenLibResponse(response)

        Assert.assertNull(response.body())
    }

    @Test
    fun handleOpenLibResponse_ResponseIsNotEmpty() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.body()).thenReturn(Mockito.mock(WorksSubj::class.java))

        presenter.handleOpenLibResponse(response)

        Assert.assertNotNull(response.body())
    }

    @Test
    fun handleOpenLibResponse_EmptyResponse() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)

        presenter.onAttach(viewContract)
        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError(TEST_DISPLAY_ERROR_NULL)
    }

    @Test
    fun handleOpenLibResponse_Success() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        val worksSubj = Mockito.mock(WorksSubj::class.java)
        val works = listOf(Mockito.mock(Work::class.java))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(worksSubj)
        Mockito.`when`(worksSubj.works).thenReturn(works)
        Mockito.`when`(worksSubj.workCount).thenReturn(TEST_TOTAL_COUNT)

        presenter.onAttach(viewContract)
        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResults(works, TEST_TOTAL_COUNT)
    }

    @After
    fun close() {
        scenario.close()
    }

}