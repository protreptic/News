package name.peterbukhal.example.news.support.reactivex

import io.reactivex.CompletableTransformer

object CompletableTransformers {

    fun schedulers(scheduler: Schedulers = Schedulers.create()) =
            CompletableTransformer {
                it.observeOn(scheduler.ui())
                  .subscribeOn(scheduler.background()) }

}