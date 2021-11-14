package ru.geekbrains.android2.openliblight.repository

import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.Author
import ru.geekbrains.android2.openliblight.model.Work
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal class FakeOpenLibRepository : RepositoryContract {

    override fun searchOpenLib(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleOpenLibResponse(Response.success(getFakeResponse()))
    }

    private fun getFakeResponse(): WorksSubj {
        val list: MutableList<Work> = mutableListOf()
        for (index in 1..49) {
            list.add(
                Work(
                    key = "Key: $index",
                    title = "Title: $index",
                    coverId = 10551974,
                    authors = listOf(Author("Key: $index", "Name: $index")),
                    subjKey = "SubjKey: $index"
                )
            )
        }
        list[29].title = "Junket"
        list[27].title = "The dog"

        return WorksSubj(
            "key",
            "dog",
            list.size.toString(),
            list
        )
    }
}
