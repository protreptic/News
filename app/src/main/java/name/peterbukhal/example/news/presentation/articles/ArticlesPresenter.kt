package name.peterbukhal.example.news.presentation.articles

import name.peterbukhal.example.news.presentation.abs.Presenter
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel

interface ArticlesPresenter : Presenter<ArticlesView> {

    fun displayArticles(query: String = "")
    fun displayArticle(article: ArticleModel)

}