package name.peterbukhal.example.news.support.reactivex

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class PrimarySchedulers : name.peterbukhal.example.news.support.reactivex.Schedulers {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    override fun background(): Scheduler = io.reactivex.schedulers.Schedulers.computation()

}