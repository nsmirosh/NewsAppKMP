package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.Serializable
import nick.mirosh.newsapp.domain.feed.model.Article


@Serializable
data class ArticleDTO(
    val source : SourceDTO? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val liked: Boolean? = null
) {

    fun toArticle() = Article(
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        liked = liked ?: false
    )
}

