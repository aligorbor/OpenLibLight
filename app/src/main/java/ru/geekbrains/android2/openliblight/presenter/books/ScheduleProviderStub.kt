package ru.geekbrains.android2.openliblight.presenter.books

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.geekbrains.android2.openliblight.presenter.SchedulerProvider

class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}