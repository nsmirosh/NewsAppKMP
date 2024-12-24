package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.save_article_error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.model.Country
import nick.mirosh.newsappkmp.domain.feed.repository.CountriesRepository
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.location.LocationData
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.ReverseGeocodingService
import org.jetbrains.compose.resources.stringResource

class FeedScreenModel(
//    private val fetchArticlesUsecase: FetchArticlesUsecase,
    private val newsRepository: NewsRepository,
//    private val likeArticleUsecase: LikeArticleUsecase,
    private val locationProvider: LocationProvider,
    private val permissionsController: PermissionsController,
    private val reverseGeocodingService: ReverseGeocodingService,
    private val dataStoreRepository: DataStoreRepository,
    private val countriesRepository: CountriesRepository
) : ScreenModel {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState.asStateFlow()

    private val _allCountries = MutableStateFlow<List<Country>?>(null)
    val allCountries: StateFlow<List<Country>?> = _allCountries.asStateFlow()

    init {
        screenModelScope.launch {
            dataStoreRepository.isFirstLaunch().collect { isFirstLaunch ->

                if (!isFirstLaunch) {
                    dataStoreRepository.getSelectedCountryCode().collect {
                        initializeCountriesList(it)
                        fetchArticles(it)
                    }
                    return@collect
                }

                dataStoreRepository.saveFirstLaunch()
                requestLocationPermissions(
                    onSuccess = {
                        getCurrentLocation { location ->
                            reverseGeocodingService.getCountryCode(
                                location.latitude,
                                location.longitude
                            ).let { code ->
                                dataStoreRepository.saveSelectedCountryCode(code ?: "US")
                                fetchArticles(code ?: "US")
                            }
                        }
                    },
                    onDenied = {
                        Logger.d("Location permission denied")
                    }
                )
            }
        }
    }


    private suspend fun initializeCountriesList(selectedCountryCode: String) {
        when (val result = countriesRepository.getCountries()) {
            is Result.Success -> {
                //TODO Mutating a list of countries, should be done in a better way
                result.data.first { it.code == selectedCountryCode }.selected = true
                _allCountries.value = result.data
            }

            is Result.Error ->
                Logger.e(
                    tag = "FeedScreenModel",
                    throwable = result.throwable,
                    messageString = "Error reading countries"
                )
        }
    }


    fun saveCountry(countryCode: String) {
        screenModelScope.launch {
            dataStoreRepository.saveSelectedCountryCode(countryCode)
        }
    }

    private suspend fun requestLocationPermissions(
        onSuccess: suspend () -> Unit = {},
        onDenied: () -> Unit = {}
    ) {
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

    private suspend fun getCurrentLocation(onLocationReceived: suspend (LocationData) -> Unit) {
        locationProvider.getCurrentLocation().collect { location ->
            location?.let {
                onLocationReceived(it)
            }
        }
    }

    private suspend fun fetchArticles(country: String) {
        _uiState.value = FeedUIState.Loading
        _uiState.value = when (val result = newsRepository.getNewsArticles(country)) {
            is Result.Success -> {
                _articles.clear()
                _articles.addAll(result.data)
                if (result.data.isNotEmpty()) FeedUIState.Feed(articles) else FeedUIState.Empty
            }

            is Result.Error -> {
                Logger.e("error = ${result.throwable.message}")
                FeedUIState.FetchingArticlesFailed
            }
        }
    }

    fun onLikeClick(article: Article) {
        screenModelScope.launch {
            val result = newsRepository.updateArticle(article.copy(liked = !article.liked))
            when (result) {
                is Result.Success -> {
                    val index = articles.indexOfFirst { it.url == result.data.url }
                    _articles[index] = result.data
                }

                is Result.Error -> {
                    _uiState.emit(FeedUIState.Error(Res.string.save_article_error))
                    Logger.e("error = ${result.throwable.message}")
                }
            }
        }
    }
}
