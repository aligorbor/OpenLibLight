package ru.geekbrains.android2.openliblight.presenter.books

import ru.geekbrains.android2.openliblight.presenter.PresenterContract

internal interface PresenterBooksContract : PresenterContract {
    fun searchOpenLib(searchQuery: String)
}