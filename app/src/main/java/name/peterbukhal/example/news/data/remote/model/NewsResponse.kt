package name.peterbukhal.example.news.data.remote.model

/**
 * Created by Peter Bukhal on 4/13/18.
 */
data class NewsResponse(
        val status: String = "",
        val totalResults: Int = 0,
        val articles: List<NewsArticle>,
        val code: String = "",
        val message: String = "")