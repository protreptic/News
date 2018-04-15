package name.peterbukhal.example.news.data.repository.source.impl

import io.reactivex.Observable
import io.reactivex.Observable.*
import io.realm.Realm
import io.realm.Sort
import name.peterbukhal.example.news.data.model.Article
import name.peterbukhal.example.news.data.model.SearchQuery
import name.peterbukhal.example.news.data.model.SearchQuery.Companion.FIELD_QUERIED_AT
import name.peterbukhal.example.news.data.model.SearchQuery.Companion.FIELD_QUERY
import name.peterbukhal.example.news.data.repository.source.ArticleDataSource

class CacheArticleDataSource : ArticleDataSource {

    override fun fetch(query: String): Observable<List<Article>> {
        Realm.getDefaultInstance().use { storage ->
            val searchQuery =
                storage.where(SearchQuery::class.java)
                    .equalTo(FIELD_QUERY, query)
                    .findFirst()

            return when (searchQuery == null || searchQuery.articles.isEmpty()) {
                true -> just(listOf())
                else -> just(storage.copyFromRealm(searchQuery!!.articles))
            }
        }
    }

    override fun fetchLast(): Observable<List<Article>> {
        Realm.getDefaultInstance().use { storage ->
            val lastQuery =
                    storage.copyFromRealm(
                    storage.where(SearchQuery::class.java)
                            .findAll()
                            .sort(FIELD_QUERIED_AT, Sort.DESCENDING))

            return when (lastQuery.isEmpty()) {
                true -> just(listOf())
                else -> just(lastQuery.first()!!.articles)
            }
        }
    }

    override fun fetchRecent(): Observable<List<String>> {
        Realm.getDefaultInstance().use { storage ->
            val recentQueries =
                    storage.copyFromRealm(
                    storage.where(SearchQuery::class.java)
                            .findAll()
                            .sort(FIELD_QUERIED_AT, Sort.DESCENDING))
                            .map { it.query }

            return when (recentQueries.isEmpty()) {
                true -> just(listOf())
                else -> just(recentQueries)
            }
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun fetchById(articleId: String): Observable<Article> =
        Realm.getDefaultInstance().use { storage ->
            val article =
                storage.where(Article::class.java)
                    .equalTo(Article.FIELD_ID, articleId)
                    .findFirst()

            return when (article == null) {
                true -> error(RuntimeException())
                else -> just(storage.copyFromRealm(article))
            }
        }

    override fun retain(article: Article): Observable<Article> =
        Realm.getDefaultInstance().use { storage ->
            storage.beginTransaction()

            val retained = storage.copyFromRealm(
                            storage.copyToRealmOrUpdate(article))

            storage.commitTransaction()

            return just(retained)
        }

}