package name.peterbukhal.example.news.presentation.articles

import name.peterbukhal.example.news.presentation.abs.Router
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel

/**
 * Created by Peter Bukhal on 4/13/18.
 */
interface ArticlesRouter : Router {

    fun toArticle(article: ArticleModel)

}