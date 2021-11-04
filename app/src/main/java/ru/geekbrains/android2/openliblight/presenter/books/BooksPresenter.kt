package ru.geekbrains.android2.openliblight.presenter.books

import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj
import ru.geekbrains.android2.openliblight.repository.RepositoryCallback
import ru.geekbrains.android2.openliblight.repository.RepositoryContract
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract

internal class BooksPresenter<V : ViewBooksContract> internal constructor(
    private val repository: RepositoryContract
) : PresenterBooksContract<V>, RepositoryCallback {

    private var viewContract: V? = null


    override fun searchOpenLib(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchOpenLib(searchQuery, this)
    }

    override fun onAttach(viewContract: V) {
        this.viewContract = viewContract
    }

    override fun onDetach() {
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
