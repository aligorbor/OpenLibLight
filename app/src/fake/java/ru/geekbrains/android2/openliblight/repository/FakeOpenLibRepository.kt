package ru.geekbrains.android2.openliblight.repository

import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal class FakeOpenLibRepository: RepositoryContract {

    override fun searchOpenLib(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleOpenLibResponse(Response.success(WorksSubj("key",
            "name",
            "49",
            listOf())))
    }
}
