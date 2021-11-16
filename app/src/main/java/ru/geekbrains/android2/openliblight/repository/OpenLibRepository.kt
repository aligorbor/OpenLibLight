package ru.geekbrains.android2.openliblight.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal class OpenLibRepository(private val openLibApi: OpenLibApi) : RepositoryContract {

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

    override fun searchOpenLib(query: String): Observable<WorksSubj> {
        return openLibApi.getWorksBySubjectRx(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun searchOpenLibAsync(query: String): WorksSubj {
        return openLibApi.getWorksBySubjectAsync(query).await()
    }

}