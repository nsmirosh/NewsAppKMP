package nick.mirosh.newsapp.domain.feed.model

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val liked: Boolean = false,
)
