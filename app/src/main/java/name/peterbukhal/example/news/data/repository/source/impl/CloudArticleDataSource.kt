package name.peterbukhal.example.news.data.repository.source.impl

import io.reactivex.Observable
import io.realm.Realm
import io.realm.Sort
import name.peterbukhal.example.news.data.model.Article
import name.peterbukhal.example.news.data.model.ArticleMapper
import name.peterbukhal.example.news.data.model.SearchQuery
import name.peterbukhal.example.news.data.model.SearchQuery.Companion.FIELD_QUERIED_AT
import name.peterbukhal.example.news.data.remote.News
import name.peterbukhal.example.news.data.repository.source.ArticleDataSource

class CloudArticleDataSource(private val news: News = News.instance) : ArticleDataSource {

    companion object {

        const val MAX_PERSISTED_QUERIES = 5L
    }

    override fun fetch(query: String): Observable<List<Article>> =
            news.everything(query)
                .map { response -> ArticleMapper.map(response.articles) }
                .flatMap { articles -> persist(query, articles) }

    private fun persist(query: String, articles: List<Article>): Observable<List<Article>> {
        Realm.getDefaultInstance().use { storage ->
            val count =
                    storage.where(SearchQuery::class.java)
                           .count()

            if (count >= MAX_PERSISTED_QUERIES) {
                storage.executeTransaction {
                    val searchQuery =
                            storage.where(SearchQuery::class.java)
                                    .findAll()
                                    .sort(FIELD_QUERIED_AT, Sort.ASCENDING)
                                    .first()

                    searchQuery?.deleteFromRealm()
                }
            }

            storage.beginTransaction()

            val persisted =
                storage.copyFromRealm(
                storage.copyToRealmOrUpdate(
                        SearchQuery().apply {
                            this.query = query
                            this.queriedAt = System.currentTimeMillis()
                            this.articles.addAll(articles)
                        }))

            storage.commitTransaction()

            return Observable.just(persisted.articles)
        }
    }

}