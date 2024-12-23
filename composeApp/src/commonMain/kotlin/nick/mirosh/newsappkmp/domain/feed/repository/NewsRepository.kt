package nick.mirosh.newsappkmp.domain.feed.repository

import kotlinx.coroutines.flow.Flow
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article

interface NewsRepository {
    suspend fun getNewsArticles(country: String): Result<List<Article>>
    fun getFavoriteArticles(): Flow<Result<List<Article>>>
    suspend fun updateArticle(article: Article): Result<Article>
}
