package ru.geekbrains.android2.openliblight.view

import ru.geekbrains.android2.openliblight.model.Work

internal interface ViewContract {
    fun displaySearchResults(
        searchResults: List<Work>,
        totalCount: String
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
