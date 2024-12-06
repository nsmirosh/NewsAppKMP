package nick.mirosh.newsapp.domain.feed.model

import nick.mirosh.newsappkmp.data.model.DatabaseArticle

data class Article(
    val author: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val liked: Boolean = false,
)

fun Article.asDatabaseModel() = DatabaseArticle(
    author = author,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)
