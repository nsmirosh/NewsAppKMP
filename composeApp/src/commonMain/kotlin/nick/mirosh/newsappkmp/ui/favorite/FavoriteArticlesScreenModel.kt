package nick.mirosh.newsappkmp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class FavoriteArticlesViewModel(
    private val repository: NewsRepository,
) : ViewModel() {

    val uiState = repository.getFavoriteArticles().map { result ->
        when (result) {
            is Result.Success ->
                if (result.data.isEmpty())
                    FavoriteArticlesUIState.FavoriteArticlesEmpty
                else
                    FavoriteArticlesUIState.FavoriteArticles(result.data)

            is Result.Error ->
                FavoriteArticlesUIState.Failed
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            FavoriteArticlesUIState.Loading
        )
}