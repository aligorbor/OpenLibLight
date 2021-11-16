package ru.geekbrains.android2.openliblight.view.books

import ru.geekbrains.android2.openliblight.model.WorksSubj

sealed class ScreenState {
    object Loading : ScreenState()
    data class Working(val searchResponse: WorksSubj) : ScreenState()
    data class Error(val error: Throwable) : ScreenState()
}
