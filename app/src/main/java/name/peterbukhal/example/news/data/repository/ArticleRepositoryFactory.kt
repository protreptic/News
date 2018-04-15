package name.peterbukhal.example.news.data.repository

import name.peterbukhal.example.news.data.repository.impl.ArticleRepositoryImpl

object ArticleRepositoryFactory {

    fun create(): ArticleRepository = ArticleRepositoryImpl()

}