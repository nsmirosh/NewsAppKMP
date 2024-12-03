package nick.mirosh.newsappkmp.domain.favorite


import kotlinx.coroutines.flow.Flow
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class FetchFavoriteArticlesUsecase (
    private val repository: NewsRepository,
) {
    suspend operator fun invoke(): Flow<Result<List<Article>>> = repository.getFavoriteArticles()
}