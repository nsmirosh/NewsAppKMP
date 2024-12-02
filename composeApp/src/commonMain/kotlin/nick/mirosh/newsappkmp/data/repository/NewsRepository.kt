package nick.mirosh.newsappkmp.data.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.model.asDatabaseModel
import nick.mirosh.newsappkmp.data.model.ArticleDTO
import nick.mirosh.newsappkmp.domain.feed.model.Source
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

const val TAG = "NewsRepository"

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: ArticleDao,
//    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(country: String): Result<List<Article>> {
        return try {
            val dtoList = newsRemoteDataSource.getHeadlines(country)
            println(
                "$TAG: getNewsArticles: dtoList.size = ${dtoList.size}"
            )
            Result.Success(dtoList.map { it.toArticle() })

        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun getFavoriteArticles() =
        try {
            Result.Success(
                newsLocalDataSource.getLikedArticles().map {
                    it.asDomainModel()
                }
//                databaseToDomainArticleMapper.map(
//                    newsLocalDataSource.getLikedArticles()
//                )
            )
        } catch (e: Exception) {
//            Log.e(TAG, e.message.toString())
            Result.Error(e)
        }

    override suspend fun updateArticle(article: Article) =
        try {
            val updatedRowId = newsLocalDataSource.insert(article.asDatabaseModel())
            if (updatedRowId != -1L)
                Result.Success(article)
            else
                Result.Error(Exception("Error updating article"))
        } catch (e: Exception) {
//            Log.e(TAG, e.message.toString())
            Result.Error(e)
        }

    private suspend fun getAllArticlesFromDb() =
        newsLocalDataSource.getAllArticles()
            .sortedByDescending { it.liked }
}


fun ArticleDTO.asDatabaseModel() = DatabaseArticle(
    author = author.orEmpty(),
    content = content.orEmpty(),
    description = description.orEmpty(),
    publishedAt = publishedAt.orEmpty(),
    title = title.orEmpty(),
    url = url.orEmpty(),
    urlToImage = urlToImage.orEmpty(),
)


@Entity(tableName = "articles")
data class DatabaseArticle(
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    val title: String,
    @PrimaryKey val url: String,
    @ColumnInfo(name = "url_to_image") val urlToImage: String,
    val liked: Boolean = false,
)

fun DatabaseArticle.asDomainModel() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage,
    liked = liked,
    source = Source("id", "name")
)
