package ru.geekbrains.android2.openliblight.presenter

import ru.geekbrains.android2.openliblight.view.ViewContract

interface PresenterContract<V: ViewContract> {
    fun onAttach(viewContract: V)
    fun onDetach()
    fun getView():V?
}