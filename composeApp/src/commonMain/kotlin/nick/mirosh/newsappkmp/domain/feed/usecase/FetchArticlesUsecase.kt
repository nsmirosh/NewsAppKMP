package nick.mirosh.newsapp.domain.feed.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class FetchArticlesUsecase(
    private val repository: NewsRepository,
//    private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(country: String): Result<List<Article>> {
        return withContext(Dispatchers.IO/*coroutineDispatcher*/) {
            repository.getNewsArticles(country)
        }
    }
}