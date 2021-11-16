package ru.geekbrains.android2.openliblight.view.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android2.openliblight.repository.OpenLibApi
import ru.geekbrains.android2.openliblight.repository.OpenLibRepository
import ru.geekbrains.android2.openliblight.repository.RepositoryContract

class BooksViewModel(
    private val repository: RepositoryContract = OpenLibRepository(
        Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY
                        )
                    ).build()
            )
            .build().create(OpenLibApi::class.java)

    )
) : ViewModel() {
    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable) })

    fun subscribeToLiveData() = liveData

    fun searchOpenLib(searchQuery: String) {
        _liveData.value = ScreenState.Loading
        viewModelCoroutineScope.launch {
            val searchResponse = repository.searchOpenLibAsync(searchQuery)
            val searchResults = searchResponse.works
            val totalCount = searchResponse.workCount
            if (searchResults != null && totalCount != null) {
                _liveData.value = ScreenState.Working(searchResponse)
            } else {
                _liveData.value =
                    ScreenState.Error(Throwable("Search results or total count are null"))
            }
        }
    }

    private fun handleError(error: Throwable) {
        _liveData.value =
            ScreenState.Error(
                Throwable(
                    error.message ?: "Response is null or unsuccessful"
                )
            )
    }

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }
}