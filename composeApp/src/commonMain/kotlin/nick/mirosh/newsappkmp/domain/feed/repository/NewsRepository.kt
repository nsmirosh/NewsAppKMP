package nick.mirosh.newsappkmp.domain.feed.repository

import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article

interface NewsRepository {
    suspend fun getNewsArticles(country: String): Result<List<Article>>
}
