package ru.geekbrains.android2.openliblight

import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.repository.OpenLibApi
import ru.geekbrains.android2.openliblight.repository.OpenLibRepository
import ru.geekbrains.android2.openliblight.repository.RepositoryCallback

class OpenLibRepositoryTest {
    private lateinit var repository: OpenLibRepository

    @Mock
    private lateinit var openLibApi: OpenLibApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = OpenLibRepository(openLibApi)
    }

    @Test
    fun searchOpenLib_Test() {
        val searchQuery = "some theme"
        val call = mock(Call::class.java) as Call<WorksSubj?>

        `when`(openLibApi.getWorksBySubject(searchQuery)).thenReturn(call)
        repository.searchOpenLib(searchQuery, mock(RepositoryCallback::class.java))
        verify(openLibApi, times(1)).getWorksBySubject(searchQuery)
    }

    @Test
    fun searchOpenLib_TestCallback() {
        val searchQuery = "some theme"
        val response = mock(Response::class.java) as Response<WorksSubj?>
        val openLibRepositoryCallBack = mock(RepositoryCallback::class.java)
        val call = object : Call<WorksSubj?> {
            override fun clone(): Call<WorksSubj?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<WorksSubj?> {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<WorksSubj?>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

        }

        `when`(openLibApi.getWorksBySubject(searchQuery)).thenReturn(call)

        repository.searchOpenLib(searchQuery, openLibRepositoryCallBack)

        verify(openLibRepositoryCallBack, times(1)).handleOpenLibResponse(response)
        verify(openLibRepositoryCallBack, times(1)).handleOpenLibError()
    }

    @Test
    fun searchOpenLib_TestCallback_WithMock() {
        val searchQuery = "some theme"
        val response = mock(Response::class.java) as Response<WorksSubj?>
        val call = mock(Call::class.java) as Call<WorksSubj?>
        val callBack = mock(Callback::class.java) as Callback<WorksSubj?>
        val openLibRepositoryCallback = mock(RepositoryCallback::class.java)

        `when`(openLibApi.getWorksBySubject(searchQuery)).thenReturn(call)
        `when`(call.enqueue(callBack)).then {
            callBack.onResponse(any(), any())
        }
        `when`(callBack.onResponse(any(), any())).then {
            openLibRepositoryCallback.handleOpenLibResponse(response)
            //    openLibRepositoryCallback.handleOpenLibError()
        }

        repository.searchOpenLib(searchQuery, openLibRepositoryCallback)

        verify(openLibRepositoryCallback, times(1)).handleOpenLibResponse(response)

    }


}