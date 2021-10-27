package ru.geekbrains.android2.openliblight

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.Work
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.books.BooksPresenter
import ru.geekbrains.android2.openliblight.repository.OpenLibRepository
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract

class BooksPresenterTest {
    private lateinit var presenter: BooksPresenter

    @Mock
    private lateinit var repository: OpenLibRepository

    @Mock
    private lateinit var viewContract: ViewBooksContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BooksPresenter(viewContract, repository)
    }

    @Test
    fun searchOpenLib_Test() {
        val searchQuery = "some theme"
        presenter.searchOpenLib(searchQuery)
        Mockito.verify(repository, Mockito.times(1)).searchOpenLib(searchQuery, presenter)
    }

    @Test
    fun handleOpenLibError_Test() {
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

        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleOpenLibResponse_ResponseFailure_ViewContractMethodOrder() {
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        Mockito.`when`(response.isSuccessful).thenReturn(false)

        presenter.handleOpenLibResponse(response)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(false)
        inOrder.verify(viewContract).displayError("Response is null or unsuccessful")
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

        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1))
            .displayError("Search results or total count are null")
    }

    @Test
    fun handleOpenLibResponse_Success() {
        val totalCount = "100"
        val response = Mockito.mock(Response::class.java) as Response<WorksSubj?>
        val worksSubj = Mockito.mock(WorksSubj::class.java)
        val works = listOf(Mockito.mock(Work::class.java))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(worksSubj)
        Mockito.`when`(worksSubj.works).thenReturn(works)
        Mockito.`when`(worksSubj.workCount).thenReturn(totalCount)

        presenter.handleOpenLibResponse(response)

        Mockito.verify(viewContract, Mockito.times(1)).displaySearchResults(works, totalCount)
    }

}