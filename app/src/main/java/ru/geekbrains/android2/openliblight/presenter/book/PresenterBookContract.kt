package ru.geekbrains.android2.openliblight.presenter.book

import ru.geekbrains.android2.openliblight.presenter.PresenterContract

internal interface PresenterBookContract : PresenterContract {
    fun setRaiting(count: Int)
    fun onIncrement()
    fun onDecrement()
}