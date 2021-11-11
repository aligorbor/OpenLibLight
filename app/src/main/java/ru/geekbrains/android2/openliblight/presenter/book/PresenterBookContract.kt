package ru.geekbrains.android2.openliblight.presenter.book

import ru.geekbrains.android2.openliblight.presenter.PresenterContract
import ru.geekbrains.android2.openliblight.view.book.ViewBookContract

internal interface PresenterBookContract<V : ViewBookContract> : PresenterContract<V> {
    fun setRaiting(count: Int)
    fun onIncrement()
    fun onDecrement()
    fun getRaiting():Int
}