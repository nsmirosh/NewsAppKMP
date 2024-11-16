package nick.mirosh.newsapp.domain.feed.model

import nick.mirosh.newsappkmp.domain.feed.model.Source

data class Article(
    val source: Source,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val liked: Boolean = false,
)
