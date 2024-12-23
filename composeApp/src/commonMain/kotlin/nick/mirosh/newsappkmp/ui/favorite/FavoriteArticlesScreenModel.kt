package nick.mirosh.newsappkmp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.favorite.FetchFavoriteArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository

class FavoriteArticlesScreenModel(
//    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
    private val repository: NewsRepository,
) : ScreenModel {

    private val _uiState =
        MutableStateFlow<FavoriteArticlesUIState>(FavoriteArticlesUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        screenModelScope.launch {
            repository.getFavoriteArticles().collect { result ->
                when (result) {
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
}
class FavoriteArticlesViewModel(
//    private val fetchFavoriteArticlesUsecase: FetchFavoriteArticlesUsecase
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

//    fun getFavoriteArticles() {
//        viewModelScope.launch {
//            repository.getFavoriteArticles().collect { result ->
//                when (result) {
//                    is Result.Success ->
//                        _uiState.value = if (result.data.isEmpty())
//                            FavoriteArticlesUIState.FavoriteArticlesEmpty
//                        else
//                            FavoriteArticlesUIState.FavoriteArticles(result.data)
//
//                    is Result.Error -> {
//                        _uiState.value = FavoriteArticlesUIState.Failed
//                        println("Error = ${result.throwable.message}")
//                    }
//                }
//            }
//        }
//    }
}
