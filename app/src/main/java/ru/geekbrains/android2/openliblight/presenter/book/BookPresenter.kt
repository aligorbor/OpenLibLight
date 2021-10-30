package ru.geekbrains.android2.openliblight.presenter.book

import ru.geekbrains.android2.openliblight.view.book.ViewBookContract

internal class BookPresenter<V : ViewBookContract> internal constructor(
    private var raiting: Int = 0
) : PresenterBookContract<V> {

    private var viewContract: V? = null

    override fun setRaiting(count: Int) {
        this.raiting = count
    }

    override fun onIncrement() {
        raiting++
        viewContract?.setRaiting(raiting)
    }

    override fun onDecrement() {
        raiting--
        viewContract?.setRaiting(raiting)
    }

    override fun onAttach(viewContract: V) {
        this.viewContract = viewContract
    }

    override fun onDetach() {
        viewContract = null
    }

    override fun getView(): V? {
        return viewContract
    }

    override fun getRaiting(): Int {
        return raiting
    }
}
