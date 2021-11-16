package ru.geekbrains.android2.openliblight.presenter.books

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geekbrains.android2.openliblight.presenter.SchedulerProvider

class SearchSchedulerProvider: SchedulerProvider {

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}