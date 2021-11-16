package ru.geekbrains.android2.openliblight.view.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.SchedulerProvider
import ru.geekbrains.android2.openliblight.presenter.books.SearchSchedulerProvider
import ru.geekbrains.android2.openliblight.repository.OpenLibApi
import ru.geekbrains.android2.openliblight.repository.OpenLibRepository
import ru.geekbrains.android2.openliblight.repository.RepositoryContract

class BooksViewModelRx(
    private val repository: RepositoryContract = OpenLibRepository(
        Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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

    ),
    private val schedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {
    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData
    private val compositeDisposable = CompositeDisposable()

    fun subscribeToLiveData() = liveData

    fun searchOpenLib(searchQuery: String) {
        compositeDisposable.add(
            repository.searchOpenLib(searchQuery)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { _liveData.value = ScreenState.Loading }
                .subscribeWith(object : DisposableObserver<WorksSubj>() {

                    override fun onNext(searchResponse: WorksSubj) {
                        val searchResults = searchResponse.works
                        val totalCount = searchResponse.workCount
                        if (searchResults != null && totalCount != null) {
                            _liveData.value = ScreenState.Working(searchResponse)

                        } else {
                            _liveData.value =
                                ScreenState.Error(Throwable("Search results or total count are null"))
                        }
                    }

                    override fun onError(e: Throwable) {
                        _liveData.value =
                            ScreenState.Error(
                                Throwable(
                                    e.message ?: "Response is null or unsuccessful"
                                )
                            )
                    }

                    override fun onComplete() {}
                }
                )
        )
    }
}