package ru.geekbrains.android2.openliblight.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.geekbrains.android2.openliblight.model.WorksSubj

internal interface OpenLibApi {

    @GET("/subjects/{subject}.json?limit=100")
    fun getWorksBySubject(@Path("subject") subject: String?): Call<WorksSubj?>?

}
