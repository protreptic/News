package name.peterbukhal.example.news.presentation.articles.article

import name.peterbukhal.example.news.presentation.abs.View
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel

/**
 * Created by Peter Bukhal on 4/13/18.
 */
interface ArticleView : View {

    fun showArticle(article: ArticleModel)

}