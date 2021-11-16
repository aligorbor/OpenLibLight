package ru.geekbrains.android2.openliblight

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.books.BooksViewModel
import ru.geekbrains.android2.openliblight.view.books.ScreenState

@ExperimentalCoroutinesApi
class BooksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var booksViewModel: BooksViewModel

    @Mock
    private lateinit var repository: RepositoryContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        booksViewModel = BooksViewModel(repository)
    }

    @Test
    fun coroutines_search_Test() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.searchOpenLibAsync(TEST_SEARCH_TEXT)).thenReturn(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    TEST_WORKS_COUNT,
                    listOf()
                )
            )
            booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
            Mockito.verify(repository, Mockito.times(1)).searchOpenLibAsync(TEST_SEARCH_TEXT)
        }
    }

    @Test
    fun coroutines_liveData_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = booksViewModel.subscribeToLiveData()

            Mockito.`when`(repository.searchOpenLibAsync(TEST_SEARCH_TEXT)).thenReturn(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    TEST_WORKS_COUNT,
                    listOf()
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
    }

    @Test
    fun coroutines_liveData_TestException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = booksViewModel.subscribeToLiveData()

            try {
                liveData.observeForever(observer)
                booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, TEST_DISPLAY_ERROR_UNSUCCESSFUL)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun coroutines_liveData_TestReturnValueErrorTotalCountIsNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = booksViewModel.subscribeToLiveData()

            Mockito.`when`(repository.searchOpenLibAsync(TEST_SEARCH_TEXT)).thenReturn(
                WorksSubj(
                    TEST_WORKS_KEY,
                    TEST_WORKS_NAME,
                    null,
                    listOf()
                )
            )
            try {
                liveData.observeForever(observer)
                booksViewModel.searchOpenLib(TEST_SEARCH_TEXT)
                val value: ScreenState.Error = liveData.value as ScreenState.Error
                Assert.assertEquals(value.error.message, TEST_DISPLAY_ERROR_NULL)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun liveData_TestReturnValueIsSuccess() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<ScreenState> {}
            val liveData = booksViewModel.subscribeToLiveData()
            val worksSubj = WorksSubj(
                TEST_WORKS_KEY,
                TEST_WORKS_NAME,
                TEST_WORKS_COUNT,
                listOf()
            )
            Mockito.`when`(repository.searchOpenLibAsync(TEST_SEARCH_TEXT)).thenReturn(
                worksSubj
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
}