package name.peterbukhal.example.news.presentation.articles.impl

import name.peterbukhal.example.news.presentation.articles.model.ArticleModel

/**
 * Created by Peter Bukhal on 4/13/18.
 */
interface ArticlesListener {

    /**
     *
     */
    fun onArticleClicked(article: ArticleModel)

}