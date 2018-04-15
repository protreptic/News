package name.peterbukhal.example.news.data.repository

import io.reactivex.Observable
import name.peterbukhal.example.news.data.model.Article

interface ArticleRepository {

    /**
     * Возвращает отсортированый (по убыванию) список последних запросов.
     *
     * @return список последних запросов
     */
    fun fetchRecent(): Observable<List<String>>

    /**
     * Возвращает список статей
     * найденных по тексту запроса.
     *
     * @param query текст запроса
     * @return найденные статьи
     */
    fun fetch(query: String, fromCache: Boolean = true): Observable<List<Article>>

    /**
     * Возвращает статью по ее идентификатору.
     *
     * @param articleId идентификатор статьи
     * @return найденная статья
     */
    fun fetchById(articleId: String): Observable<Article>

    /**
     * Сохраняет статью в хранилище. В
     * случае успеха возвращает ее.
     *
     * @param article статья
     * @return сохраненная статья
     */
    fun retain(article: Article): Observable<Article>

}