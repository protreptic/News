package name.peterbukhal.example.news.support.reactivex

import io.reactivex.Scheduler

interface Schedulers {

    companion object {

        fun create(): Schedulers = PrimarySchedulers()

    }

    fun ui(): Scheduler
    fun background(): Scheduler

}