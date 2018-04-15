package name.peterbukhal.example.news.data.repository.source.impl

import io.reactivex.Observable
import io.realm.Realm
import name.peterbukhal.example.news.data.model.Article
import name.peterbukhal.example.news.data.model.SearchQuery
import name.peterbukhal.example.news.data.model.SearchQuery.Companion.FIELD_QUERY
import name.peterbukhal.example.news.data.repository.source.ArticleDataSource

class CacheArticleDataSource : ArticleDataSource {

    override fun fetch(query: String): Observable<List<Article>> {
        Realm.getDefaultInstance().use { realm ->
            val searchQuery =
                realm.where(SearchQuery::class.java)
                    .equalTo(FIELD_QUERY, query)
                    .findFirst()

            return when (searchQuery == null || searchQuery.articles.isEmpty()) {
                true -> Observable.just(listOf())
                else -> Observable.just(realm.copyFromRealm(searchQuery!!.articles))
            }
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun fetchById(articleId: String): Observable<Article> =
        Realm.getDefaultInstance().use { realm ->
            val article =
                realm.where(Article::class.java)
                    .equalTo(Article.FIELD_ID, articleId)
                    .findFirst()

            return when (article == null) {
                true -> Observable.error(RuntimeException())
                else -> Observable.just(realm.copyFromRealm(article))
            }
        }

    override fun retain(article: Article): Observable<Article> =
        Realm.getDefaultInstance().use { storage ->
            storage.beginTransaction()

            val retained = storage.copyFromRealm(
                            storage.copyToRealmOrUpdate(article))

            storage.commitTransaction()

            return Observable.just(retained)
        }

}