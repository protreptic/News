package name.peterbukhal.example.news.presentation.articles.article

import name.peterbukhal.example.news.presentation.abs.Presenter

/**
 * Created by Peter Bukhal on 4/13/18.
 */
interface ArticlePresenter : Presenter<ArticleView> {

    fun displayArticle(articleId: String)

}