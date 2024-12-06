package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.location.LocationProvider

class FeedScreenModel(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
    private val locationProvider: LocationProvider,
    private val permissionsController: PermissionsController
) : ScreenModel {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState

    init {
        requestLocationPermissions()
        screenModelScope.launch {
            fetchArticles("ua")
        }
    }

    fun requestLocationPermissions() {
        //TODO in this app we're assuming that the user grants the permission
        // but in a real app you should handle the permission denial with
        // a proper UI/UX - more info https://developer.android.com/training/permissions/requesting
        screenModelScope.launch {
            try {
                permissionsController.providePermission(Permission.COARSE_LOCATION)
                // Permission has been granted successfully.
            } catch (deniedAlways: DeniedAlwaysException) {
                // Permission is always denied.
            } catch (denied: DeniedException) {
                // Permission was denied.
            }

            locationProvider.getCurrentLocation().collect {
                println("location = $it")
            }
        }
    }

    private suspend fun fetchArticles(country: String) {
        _uiState.value = FeedUIState.Loading
        _uiState.value = when (val result = fetchArticlesUsecase(country)) {
            is Result.Success -> {
                _articles.clear()
                _articles.addAll(result.data)
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
