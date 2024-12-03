package nick.mirosh.newsappkmp.ui.favorite

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.favorite.FetchFavoriteArticlesUsecase

class FavoriteArticlesScreenModel (
    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
) : ScreenModel {

    private val _uiState = MutableStateFlow<FavoriteArticlesUIState>(FavoriteArticlesUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        screenModelScope.launch {
            when (val result = fetchFavoriteArticlesUsecase()) {
                is Result.Success ->
                    _uiState.value = if (result.data.isEmpty())
                        FavoriteArticlesUIState.FavoriteArticlesEmpty
                    else
                        FavoriteArticlesUIState.FavoriteArticles(result.data)

                is Result.Error -> {
                    _uiState.value = FavoriteArticlesUIState.Failed
                    println("Error = ${result.throwable.message}")
                }
            }
        }
    }
}