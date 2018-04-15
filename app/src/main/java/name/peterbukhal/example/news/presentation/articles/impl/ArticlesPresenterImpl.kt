package name.peterbukhal.example.news.presentation.articles.impl

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import name.peterbukhal.example.news.data.repository.ArticleRepository
import name.peterbukhal.example.news.data.repository.ArticleRepositoryFactory
import name.peterbukhal.example.news.presentation.articles.ArticlesPresenter
import name.peterbukhal.example.news.presentation.articles.ArticlesRouter
import name.peterbukhal.example.news.presentation.articles.ArticlesView
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel
import name.peterbukhal.example.news.presentation.articles.model.ArticleModelMapper
import name.peterbukhal.example.news.support.reactivex.ObservableTransformers
import name.peterbukhal.example.news.support.reactivex.Schedulers

class ArticlesPresenterImpl(
        private val scheduler: Schedulers = Schedulers.create(),
        private val router: ArticlesRouter,
        private val repository: ArticleRepository = ArticleRepositoryFactory.create()) : ArticlesPresenter {

    private lateinit var attachedView: ArticlesView
    private val disposables = CompositeDisposable()

    override fun attachView(view: ArticlesView) {
        attachedView = view

        displayArticles()
    }

    override fun displayArticles(query: String) {
        disposables.add(
            repository.fetch(query)
              .compose(ObservableTransformers.schedulers(scheduler))
              .map(ArticleModelMapper::map)
              .doOnSubscribe { attachedView.showLoading() }
              .subscribe({ articles -> showArticles(articles) },
                            { attachedView.showError() }))
    }

    private fun showArticles(articles: List<ArticleModel>) {
        when (articles.isEmpty()) {
            true -> attachedView.showEmpty()
            else -> attachedView.showArticles(articles)
        }
    }

    override fun displayArticle(article: ArticleModel) {
        disposables.add(
            repository
                .fetchById(article.id)
                .flatMap { repository.retain(it.apply { viewed = true }) }
                .map(ArticleModelMapper::map)
                .compose(ObservableTransformers.schedulers(scheduler))
                .subscribe({ router.toArticle(it) },
                                { attachedView.showError() }))
    }

    override fun detachView() {
        disposables.dispose()
    }

}