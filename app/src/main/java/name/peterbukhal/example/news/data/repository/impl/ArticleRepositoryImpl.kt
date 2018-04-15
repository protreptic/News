package name.peterbukhal.example.news.data.repository.impl

import io.reactivex.Observable
import name.peterbukhal.example.news.data.model.Article
import name.peterbukhal.example.news.data.repository.ArticleRepository
import name.peterbukhal.example.news.data.repository.source.ArticleDataSource
import name.peterbukhal.example.news.data.repository.source.ArticleDataSourceFactory

class ArticleRepositoryImpl(
        private val cache: ArticleDataSource = ArticleDataSourceFactory.createCache(),
        private val cloud: ArticleDataSource = ArticleDataSourceFactory.createCloud()) : ArticleRepository {

    override fun fetch(query: String, fromCache: Boolean): Observable<List<Article>> =
            when (fromCache) {
                true -> cache.fetch(query)
                            .flatMap {
                                when (query.isNotEmpty() && it.isEmpty()) {
                                    true -> fetch(query, false)
                                    else -> Observable.just(it)
                                }}
                else -> cloud.fetch(query)
            }

    override fun fetchById(articleId: String): Observable<Article> =
            cache.fetchById(articleId)

    override fun retain(article: Article): Observable<Article> =
            cache.retain(article)

}