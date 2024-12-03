package nick.mirosh.newsappkmp.data.repository

import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.model.asDatabaseModel
import nick.mirosh.newsappkmp.data.model.ArticleDTO
import nick.mirosh.newsappkmp.data.model.DatabaseArticle
import nick.mirosh.newsappkmp.data.model.asDomainModel
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

const val TAG = "NewsRepository"

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: ArticleDao,
//    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(country: String): Result<List<Article>> {
        return try {
            newsRemoteDataSource.getHeadlines(country).let { articleDtos ->
                newsLocalDataSource.insertAll(articleDtos.map { it.asDatabaseModel() })
            }
            Result.Success(newsLocalDataSource.getAllArticles().map { it.asDomainModel() })
        } catch (e: Exception) {
            val result = newsLocalDataSource.getAllArticles().map { it.asDomainModel() }
            if (result.isNotEmpty()) {
                Result.Success(result)
            }
            else {
                e.printStackTrace()
                Result.Error(e)
            }
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



