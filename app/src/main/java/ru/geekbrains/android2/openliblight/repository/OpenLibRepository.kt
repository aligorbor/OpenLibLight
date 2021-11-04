package ru.geekbrains.android2.openliblight.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal class OpenLibRepository(private val openLibApi: OpenLibApi):RepositoryContract {

    override fun searchOpenLib(
        query: String,
        callback: RepositoryCallback
    ) {
        val call = openLibApi.getWorksBySubject(query)
        call?.enqueue(object : Callback<WorksSubj?> {

            override fun onResponse(
                call: Call<WorksSubj?>,
                response: Response<WorksSubj?>
            ) {
                callback.handleOpenLibResponse(response)
            }

            override fun onFailure(
                call: Call<WorksSubj?>,
                t: Throwable
            ) {
                callback.handleOpenLibError()
            }
        })
    }

}