package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase

class FeedScreenModel(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
) : ScreenModel {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState

    init {
        screenModelScope.launch {
            fetchArticles()
        }
    }

    private suspend fun fetchArticles() {
        _uiState.value = FeedUIState.Loading
        _uiState.value = when (val result = fetchArticlesUsecase("us")) {
            is Result.Success -> {
                _articles.clear()
                _articles.addAll(result.data.filterNot { it.title.lowercase().contains("removed") })
                if (result.data.isNotEmpty()) FeedUIState.Feed(articles) else FeedUIState.Empty
            }

            is Result.Error -> {
                println("error = ${result.throwable.message}")
                FeedUIState.Failed
            }
        }
    }

   //https://stackoverflow.com/questions/74699081/jetpack-compose-lazy-column-all-items-recomposes-when-a-single-item-update
   fun onLikeClick(article: Article) {
       screenModelScope.launch {
           when (val result = likeArticleUsecase(article)) {
               is Result.Success -> {
                   val index = articles.indexOfFirst { it.url == result.data.url }
                   _articles[index] = result.data
               }

               is Result.Error -> {
                     println("error = ${result.throwable.message}")
               }
           }
       }
   }
}
