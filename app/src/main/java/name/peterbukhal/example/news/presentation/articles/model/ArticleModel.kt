package name.peterbukhal.example.news.presentation.articles.model

data class ArticleModel(
        val id: String = "",
        val imageUri: String = "",
        val title: String = "",
        val description: String = "",
        val url: String = "",
        var viewed: Boolean = false,
        val publishedAt: String = "")