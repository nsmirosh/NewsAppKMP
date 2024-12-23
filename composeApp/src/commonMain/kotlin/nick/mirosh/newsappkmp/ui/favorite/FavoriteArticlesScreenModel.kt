package nick.mirosh.newsappkmp.ui.favorite

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class FavoriteArticlesScreenModel(
    repository: NewsRepository,
) : ScreenModel {

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
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            FavoriteArticlesUIState.Loading
        )
}