package name.peterbukhal.example.news.data.remote.model

/**
 * Created by Peter Bukhal on 4/13/18.
 */
data class NewsArticle(
        val id: Int = 0,
        val urlToImage: String?,
        val publishedAt: String?,
        val title: String? = "",
        val description: String? = "",
        val url: String? = "")