package ru.geekbrains.android2.openliblight.view.books

import ru.geekbrains.android2.openliblight.model.Work

internal interface ViewBooksContract {
    fun displaySearchResults(
        searchResults: List<Work>,
        totalCount: String
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
