package name.peterbukhal.example.news.data.repository.source

import io.reactivex.Observable
import name.peterbukhal.example.news.data.model.Article

interface ArticleDataSource {

    /**
     * Возвращает список статей
     * найденных по тексту запроса.
     *
     * @param query текст запроса
     * @return найденные статьи
     */
    fun fetch(query: String): Observable<List<Article>>

    /**
     * Возвращает результат последнего запроса.
     *
     * @return результат последнего запроса
     */
    fun fetchLast(): Observable<List<Article>> = Observable.empty()

    /**
     * Возвращает отсортированый (по убыванию) список последних запросов.
     *
     * @return список последних запросов
     */
    fun fetchRecent(): Observable<List<String>> = Observable.empty()

    /**
     * Возвращает статью по ее идентификатору.
     *
     * @param articleId идентификатор статьи
     * @return найденная статья
     */
    fun fetchById(articleId: String): Observable<Article> = Observable.empty()

    /**
     * Сохраняет статью в хранилище. В
     * случае успеха возвращает ее.
     *
     * @param article статья
     * @return сохраненная статья
     */
    fun retain(article: Article): Observable<Article> = Observable.empty()

}