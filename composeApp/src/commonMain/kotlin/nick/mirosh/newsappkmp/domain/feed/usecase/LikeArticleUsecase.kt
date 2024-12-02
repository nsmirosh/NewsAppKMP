package nick.mirosh.newsappkmp.domain.feed.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class LikeArticleUsecase (
    private val repository: NewsRepository,
//    private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(article: Article): Result<Article> {
        return withContext(Dispatchers.Default/*coroutineDispatcher*/) {
            repository.updateArticle(article.copy(liked = !article.liked))
        }
    }
}