package ru.geekbrains.android2.openliblight.presenter.book

import ru.geekbrains.android2.openliblight.view.book.ViewDetailsContract

internal class BookPresenter internal constructor(
    private val viewContract: ViewDetailsContract,
    private var count: Int = 0
) : PresenterBookContract {

    override fun setRaiting(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }
}
