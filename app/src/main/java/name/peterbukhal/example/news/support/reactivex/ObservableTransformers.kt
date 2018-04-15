package name.peterbukhal.example.news.support.reactivex

import io.reactivex.ObservableTransformer

object ObservableTransformers {

    fun <Any> schedulers(scheduler: Schedulers = Schedulers.create()) =
            ObservableTransformer<Any, Any> {
                it.observeOn(scheduler.ui())
                  .subscribeOn(scheduler.background()) }

}