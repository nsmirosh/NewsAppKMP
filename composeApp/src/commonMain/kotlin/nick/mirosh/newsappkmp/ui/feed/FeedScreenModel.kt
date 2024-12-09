package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.ReverseGeocodingService

class FeedScreenModel(
    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val likeArticleUsecase: LikeArticleUsecase,
    private val locationProvider: LocationProvider,
    private val permissionsController: PermissionsController,
    private val reverseGeocodingService: ReverseGeocodingService,
    private val dataStoreRepository: DataStoreRepository
) : ScreenModel {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState

    val country = dataStoreRepository.getCountry()

    init {
        screenModelScope.launch {
            requestLocationPermissions(
                onSuccess = { getCurrentLocation() },
                onDenied = {
                    Logger.d("Location permission denied")
                }
            )
            country.collect {
                Logger.d("saved country = $it")
            }
        }
    }


    fun saveCountry(value: String) {
        screenModelScope.launch {
            dataStoreRepository.saveCountry(value)
        }
    }

    private suspend fun requestLocationPermissions(
        onSuccess: suspend () -> Unit = {},
        onDenied: () -> Unit = {}
    ) {

        Logger.d("Requesting location permissions")
        //TODO in this app we're assuming that the user grants the permission
        // but in a real app you should handle the permission denial with
        // a proper UI/UX - more info https://developer.android.com/training/permissions/requesting

        if (permissionsController.getPermissionState(Permission.COARSE_LOCATION) == PermissionState.Granted) {
            onSuccess()
            return
        }

        //TODO: Hack to overcome the moko libraries' bug for ios first-time permission requst
        screenModelScope.launch(Dispatchers.IO) {
            while (permissionsController.getPermissionState(Permission.COARSE_LOCATION) != PermissionState.Granted) {
                delay(200)
            }
            onSuccess()
        }
        try {
            permissionsController.providePermission(Permission.COARSE_LOCATION)
        } catch (e: Exception) {
            Logger.e("Error requesting location permissions: ${e.message}")
            onDenied()
        }
    }

    private suspend fun getCurrentLocation() {
        locationProvider.getCurrentLocation().collect { location ->
            Logger.d("current location = $location")
            location?.let {
                val countryCode = reverseGeocodingService.getCountryCode(
                    location.latitude,
                    location.longitude
                )

                Logger.d("received country code = $location")
                fetchArticles(
                    countryCode
                        ?: "ua"
                )
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
