package ru.geekbrains.android2.openliblight.repository

internal interface RepositoryContract {
    fun searchOpenLib(
        query: String,
        callback: RepositoryCallback
    )
}