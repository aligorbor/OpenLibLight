package ru.geekbrains.android2.openliblight.repository

import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal interface OpenLibApi {

    @GET("/subjects/{subject}.json?limit=100")
    fun getWorksBySubject(@Path("subject") subject: String?): Call<WorksSubj?>?

    @GET("/subjects/{subject}.json?limit=100")
    fun getWorksBySubjectRx(@Path("subject") subject: String?): Observable<WorksSubj>

    @GET("/subjects/{subject}.json?limit=100")
    fun getWorksBySubjectAsync(@Path("subject") subject: String?): Deferred<WorksSubj>
}
