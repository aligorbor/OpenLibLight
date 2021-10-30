package ru.geekbrains.android2.openliblight.presenter.books

import ru.geekbrains.android2.openliblight.presenter.PresenterContract
import ru.geekbrains.android2.openliblight.view.books.ViewBooksContract

internal interface PresenterBooksContract<V : ViewBooksContract> : PresenterContract<V> {
    fun searchOpenLib(searchQuery: String)
}