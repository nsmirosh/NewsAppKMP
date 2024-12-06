package nick.mirosh.newsappkmp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nick.mirosh.newsapp.domain.feed.model.Article

@Entity(tableName = "articles")
data class DatabaseArticle(
    val author: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    val title: String,
    @PrimaryKey val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    val liked: Boolean = false,
)

fun DatabaseArticle.asDomainModel() = Article(
    author = author,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
)
