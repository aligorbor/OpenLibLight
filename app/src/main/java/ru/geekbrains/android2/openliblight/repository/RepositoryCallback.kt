package ru.geekbrains.android2.openliblight.repository

import retrofit2.Response
import ru.geekbrains.android2.openliblight.model.WorksSubj

interface RepositoryCallback {
    fun handleOpenLibResponse(response: Response<WorksSubj?>?)
    fun handleOpenLibError()
}