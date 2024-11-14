package nick.mirosh.newsappkmp.data.repository

import nick.mirosh.newsapp.domain.ErrorType
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

const val TAG = "NewsRepository"

class NewsRepositoryImpl (
    private val newsRemoteDataSource: NewsRemoteDataSource,
//    private val databaseToDomainArticleMapper: DatabaseToDomainArticleMapper,
) : NewsRepository {
    override suspend fun getNewsArticles(country: String): Result<List<Article>> {
        return try {
            val dtoList = newsRemoteDataSource.getHeadlines(country)
            println(
                "$TAG: getNewsArticles: dtoList.size = ${dtoList.size}"
            )
            Result.Success(dtoList.map{ it.toArticle() })

        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}



