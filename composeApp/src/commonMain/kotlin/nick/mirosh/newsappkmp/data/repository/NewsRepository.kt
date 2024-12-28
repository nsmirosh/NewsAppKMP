package nick.mirosh.newsappkmp.data.repository

import kotlinx.coroutines.flow.map
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.model.asDatabaseModel
import nick.mirosh.newsappkmp.data.model.asDomainModel
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

const val TAG = "NewsRepository"

class NewsRepositoryImpl(
    private val newRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: ArticleDao,
) : NewsRepository {
    override suspend fun getNewsArticles(
        country: String?,
        category: String?
    ): Result<List<Article>> {
        try {
            newRemoteDataSource.getHeadlines(country, category).let { articleDtos ->
                val databaseArticles = articleDtos
                    .filterNot { it.duplicate ?: false }
                    .map { it.asDatabaseModel() }
                newsLocalDataSource.deleteAll()
                newsLocalDataSource.insertAll(databaseArticles)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val databaseArticles = getAllArticlesFromDb().map { it.asDomainModel() }

        return if (databaseArticles.isNotEmpty())
            Result.Success(databaseArticles)
        else Result.Error(
            Exception("Error fetching articles")
        )
    }

    override fun getFavoriteArticles() =
        newsLocalDataSource.getLikedArticles().map { articles ->
            Result.Success(articles?.map { it.asDomainModel() } ?: listOf())
        }

    override suspend fun updateArticle(article: Article) =
        try {
            val updatedRowId = newsLocalDataSource.insert(article.asDatabaseModel())
            if (updatedRowId != -1L)
                Result.Success(article)
            else
                Result.Error(Exception("Error updating article"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }

    private suspend fun getAllArticlesFromDb() =
        newsLocalDataSource.getAllArticles()
            .sortedByDescending { it.liked }
}



