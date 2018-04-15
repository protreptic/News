package name.peterbukhal.example.news.data.model

import name.peterbukhal.example.news.data.remote.model.NewsArticle
import java.util.*

/**
 * Created by Peter Bukhal on 4/13/18.
 */
object ArticleMapper {

    fun map(article: NewsArticle) =
            Article().apply {
                urlToImage = article.urlToImage ?: "http://via.placeholder.com/350x150"
                publishedAt = article.publishedAt ?: "1970-01-01T00:00:00Z"
                title = article.title ?: ""
                description = article.description ?: ""
                url = article.url ?: ""
                id = UUID.nameUUIDFromBytes(url.toByteArray()).toString()
            }

    fun map(articles: List<NewsArticle>) =
            articles.map { article -> map(article) }

}