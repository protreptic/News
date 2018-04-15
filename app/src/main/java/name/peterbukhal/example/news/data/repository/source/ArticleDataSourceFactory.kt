package name.peterbukhal.example.news.data.repository.source

import name.peterbukhal.example.news.data.repository.source.impl.CacheArticleDataSource
import name.peterbukhal.example.news.data.repository.source.impl.CloudArticleDataSource

object ArticleDataSourceFactory {

    fun createCache(): ArticleDataSource = CacheArticleDataSource()
    fun createCloud(): ArticleDataSource = CloudArticleDataSource()
}