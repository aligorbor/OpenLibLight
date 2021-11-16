package ru.geekbrains.android2.openliblight.repository

import io.reactivex.Observable
import ru.geekbrains.android2.openliblight.model.WorksSubj

interface RepositoryContract {

    fun searchOpenLib(
        query: String,
        callback: RepositoryCallback
    )

    fun searchOpenLib(
        query: String
    ): Observable<WorksSubj>

    suspend fun searchOpenLibAsync(
        query: String
    ): WorksSubj

}