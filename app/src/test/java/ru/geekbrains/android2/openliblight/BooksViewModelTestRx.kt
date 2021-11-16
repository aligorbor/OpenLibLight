package ru.geekbrains.android2.openliblight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.books.ScheduleProviderStub
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.books.BooksViewModel
import ru.geekbrains.android2.openliblight.view.books.BooksViewModelRx
import ru.geekbrains.android2.openliblight.view.books.ScreenState


class BooksViewModelTestRx {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var booksViewModel: BooksViewModelRx

    @Mock
    private lateinit var repository: RepositoryContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booksViewModel = BooksViewModelRx(repository, ScheduleProviderStub())
    }

    @Test
    fun search_Test() {
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

        booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
        Mockito.verify(repository, Mockito.times(1)).searchOpenLib(TEST_SEARCH_TEXT)
    }

    @Test
    fun liveData_TestReturnValueIsNotNull() {
        val observer = Observer<ScreenState> {}
        val liveData = booksViewModel.subscribeToLiveData()

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

        try {
            liveData.observeForever(observer)
            booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
            Assert.assertNotNull(liveData.value)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun liveData_TestReturnValueIsError() {
        val observer = Observer<ScreenState> {}
        val liveData = booksViewModel.subscribeToLiveData()
        val error = Throwable(TEST_DISPLAY_ERROR)

        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.error(error)
        )

        try {
            liveData.observeForever(observer)
            booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
            val value: ScreenState.Error = liveData.value as ScreenState.Error
            Assert.assertEquals(value.error.message, error.message)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun liveData_TestReturnValueErrorTotalCountIsNull() {
        val observer = Observer<ScreenState> {}
        val liveData = booksViewModel.subscribeToLiveData()
        val error = Throwable(TEST_DISPLAY_ERROR_NULL)

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

        try {
            liveData.observeForever(observer)
            booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
            val value: ScreenState.Error = liveData.value as ScreenState.Error
            Assert.assertEquals(value.error.message, error.message)
        } finally {
            liveData.removeObserver(observer)
        }
    }

    @Test
    fun liveData_TestReturnValueIsSuccess() {
        val observer = Observer<ScreenState> {}
        val liveData = booksViewModel.subscribeToLiveData()
        val worksSubj = WorksSubj(
            TEST_WORKS_KEY,
            TEST_WORKS_NAME,
            TEST_WORKS_COUNT,
            listOf()
        )
        Mockito.`when`(repository.searchOpenLib(TEST_SEARCH_TEXT)).thenReturn(
            Observable.just(
                worksSubj
            )
        )
        try {
            liveData.observeForever(observer)
            booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
            val value: ScreenState.Working = liveData.value as ScreenState.Working
            Assert.assertEquals(value, ScreenState.Working(worksSubj))
        } finally {
            liveData.removeObserver(observer)
        }
    }
}