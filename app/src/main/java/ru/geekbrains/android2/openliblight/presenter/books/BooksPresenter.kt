package ru.geekbrains.android2.openliblight.presenter.books

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.presenter.SchedulerProvider
import ru.geekbrains.android2.openliblight.repository.RepositoryCallback
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract

internal class BooksPresenter<V : ViewBooksContract> internal constructor(
    private val repository: RepositoryContract,
    private val schedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : PresenterBooksContract<V>, RepositoryCallback {

    private var viewContract: V? = null
    private val compositeDisposable = CompositeDisposable()

//    override fun searchOpenLib(searchQuery: String) {
//        viewContract?.displayLoading(true)
//        repository.searchOpenLib(searchQuery, this)
//    }

    override fun searchOpenLib(searchQuery: String) {
        compositeDisposable.add(
            repository.searchOpenLib(searchQuery)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { viewContract?.displayLoading(true) }
                .doOnTerminate { viewContract?.displayLoading(false) }
                .subscribeWith(object : DisposableObserver<WorksSubj>() {

                    override fun onNext(searchResponse: WorksSubj) {
                        val searchResults = searchResponse.works
                        val totalCount = searchResponse.workCount
                        if (searchResults != null && totalCount != null) {
                            viewContract?.displaySearchResults(
                                searchResults,
                                totalCount
                            )
                        } else {
                            viewContract?.displayError("Search results or total count are null")
                        }
                    }

                    override fun onError(e: Throwable) {
                        viewContract?.displayError(e.message ?: "Response is null or unsuccessful")
                    }

                    override fun onComplete() {}
                }
                )
        )
    }


    override fun onAttach(viewContract: V) {
        this.viewContract = viewContract
    }

    override fun onDetach() {
        compositeDisposable.clear()
        viewContract = null
    }

    override fun handleOpenLibResponse(response: Response<WorksSubj?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.works
            val totalCount = searchResponse?.workCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleOpenLibError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }

    override fun getView(): V? {
        return viewContract
    }
}
