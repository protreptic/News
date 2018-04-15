package name.peterbukhal.example.news.presentation.articles.model

import name.peterbukhal.example.news.data.model.Article

/**
 * Created by Peter Bukhal on 4/13/18.
 */
object ArticleModelMapper {

        fun map(article: Article) =
                ArticleModel(
                        article.id,
                        article.urlToImage,
                        article.title,
                        article.description,
                        article.url,
                        article.viewed,
                        article.publishedAt)

        fun map(articles: List<Article>) =
                articles.map { article -> map(article) }

}