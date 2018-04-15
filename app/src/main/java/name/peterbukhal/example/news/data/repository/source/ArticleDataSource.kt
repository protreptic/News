package name.peterbukhal.example.news.data.repository.source

import io.reactivex.Observable
import name.peterbukhal.example.news.data.model.Article

interface ArticleDataSource {

    /**
     *
     */
    fun fetch(query: String): Observable<List<Article>>

    /**
     *
     */
    fun fetchById(articleId: String): Observable<Article> = Observable.empty()

    /**
     *
     */
    fun retain(article: Article): Observable<Article> = Observable.empty()

}