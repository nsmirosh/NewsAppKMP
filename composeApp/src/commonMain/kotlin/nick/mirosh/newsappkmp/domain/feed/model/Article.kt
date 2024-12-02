package nick.mirosh.newsapp.domain.feed.model

import nick.mirosh.newsappkmp.data.repository.DatabaseArticle
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

fun Article.asDatabaseModel() = DatabaseArticle(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)
