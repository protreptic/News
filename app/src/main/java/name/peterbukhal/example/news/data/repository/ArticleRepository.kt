package name.peterbukhal.example.news.data.repository

import io.reactivex.Observable
import name.peterbukhal.example.news.data.model.Article

interface ArticleRepository {

    /**
     *
     */
    fun fetch(query: String, fromCache: Boolean = true): Observable<List<Article>>

    /**
     *
     */
    fun fetchById(articleId: String): Observable<Article>

    /**
     *
     */
    fun retain(article: Article): Observable<Article>

}