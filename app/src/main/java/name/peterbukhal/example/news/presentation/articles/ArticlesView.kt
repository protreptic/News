package name.peterbukhal.example.news.presentation.articles

import name.peterbukhal.example.news.presentation.abs.View
import name.peterbukhal.example.news.presentation.articles.model.ArticleModel

interface ArticlesView : View {

    fun showArticles(articles: List<ArticleModel>)
    fun showRecentQuery(queries: List<String>)

    fun showLoading()
    fun showEmpty()
    fun showError()

}