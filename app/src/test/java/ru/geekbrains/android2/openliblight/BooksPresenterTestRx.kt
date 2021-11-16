package ru.geekbrains.android2.openliblight

import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.books.BooksPresenter
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.presenter.books.ScheduleProviderStub
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract


class BooksPresenterTestRx {
    private lateinit var presenter: BooksPresenter<ViewBooksContract>

    @Mock
    private lateinit var repository: RepositoryContract

    @Mock
    private lateinit var viewContract: ViewBooksContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BooksPresenter(repository, ScheduleProviderStub())
    }

    @Test
    fun searchOpenLib_Test() {

        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    TEST_WORKS_COUNT,
                    listOf()
                )
            )
        )

        presenter.searchOpenLib(TEST_SEARCH_TEXT)
        Mockito.verify(repository, Mockito.times(1)).searchOpenLib(TEST_SEARCH_TEXT)
    }

    @Test
    fun handleRequestError_Test() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.error(Throwable(TEST_DISPLAY_ERROR))
        )
        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)
        Mockito.verify(viewContract, Mockito.times(1)).displayError(TEST_DISPLAY_ERROR)
    }

    @Test
    fun handleResponseError_ViewContractMethodOrder() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.error(Throwable(TEST_DISPLAY_ERROR))
        )

        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(true)
        inOrder.verify(viewContract).displayError(TEST_DISPLAY_ERROR)
    }


    @Test
    fun handleResponseError_TotalCountIsNull() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    null,
                    listOf()
                )
            )
        )
        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)
        Mockito.verify(viewContract, Mockito.times(1)).displayError(TEST_DISPLAY_ERROR_NULL)
    }

    @Test
    fun handleResponseError_TotalCountIsNull_ViewContractMethodOrder() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    null,
                    listOf()
                )
            )
        )

        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(true)
        inOrder.verify(viewContract).displayError(TEST_DISPLAY_ERROR_NULL)
        inOrder.verify(viewContract).displayLoading(false)
    }

    @Test
    fun handleResponseSuccess() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    TEST_WORKS_COUNT,
                    listOf()
                )
            )
        )
        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)
        Mockito.verify(viewContract, Mockito.times(1))
            .displaySearchResults(listOf(), TEST_WORKS_COUNT)
    }

    @Test
    fun handleResponseSuccess_ViewContractMethodOrder() {
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    TEST_WORKS_COUNT,
                    listOf()
                )
            )
        )

        presenter.onAttach(viewContract)
        presenter.searchOpenLib(TEST_SEARCH_TEXT)

        val inOrder = Mockito.inOrder(viewContract)
        inOrder.verify(viewContract).displayLoading(true)
        inOrder.verify(viewContract).displaySearchResults(listOf(), TEST_WORKS_COUNT)
        inOrder.verify(viewContract).displayLoading(false)
    }


}