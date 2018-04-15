package name.peterbukhal.example.news.presentation.articles.article.impl

import io.reactivex.disposables.CompositeDisposable
import name.peterbukhal.example.news.data.repository.ArticleRepository
import name.peterbukhal.example.news.data.repository.ArticleRepositoryFactory
import name.peterbukhal.example.news.presentation.articles.article.ArticlePresenter
import name.peterbukhal.example.news.presentation.articles.article.ArticleView
import name.peterbukhal.example.news.presentation.articles.model.ArticleModelMapper
import name.peterbukhal.example.news.support.reactivex.ObservableTransformers
import name.peterbukhal.example.news.support.reactivex.Schedulers

/**
 * Created by Peter Bukhal on 4/13/18.
 */
class ArticlePresenterImpl(
        private val scheduler: Schedulers = Schedulers.create(),
        private val repository: ArticleRepository = ArticleRepositoryFactory.create()) : ArticlePresenter {

    private lateinit var attachedView: ArticleView
    private val disposables = CompositeDisposable()

    override fun attachView(view: ArticleView) {
        attachedView = view
    }

    override fun displayArticle(articleId: String) {
        disposables.add(
            repository
                .fetchById(articleId)
                .map(ArticleModelMapper::map)
                .compose(ObservableTransformers.schedulers(scheduler))
                .subscribe({ article -> attachedView.showArticle(article) }, { }))
    }

    override fun detachView() {
        disposables.dispose()
    }

}