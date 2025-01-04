package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.save_article_error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.data.manager.PermissionManager
import nick.mirosh.newsappkmp.domain.feed.model.Country
import nick.mirosh.newsappkmp.domain.feed.repository.CountriesRepository
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.location.LocationData
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.ReverseGeocodingService
import nick.mirosh.newsappkmp.ui.utils.DialogProvider

val CATEGORIES = listOf(
    "business",
    "world",
    "education",
    "entertainment",
    "environment",
    "food",
    "health",
    "domestic",
    "lifestyle",
    "crime",
    "politics",
    "science",
    "sports",
    "technology",
    "top",
    "tourism",
    "other",
)

class FeedScreenModel(
    private val newsRepository: NewsRepository,
    private val locationProvider: LocationProvider,
    private val reverseGeocodingService: ReverseGeocodingService,
    private val dataStoreRepository: DataStoreRepository,
    private val countriesRepository: CountriesRepository,
    private val permissionsManager: PermissionManager,
//    private val dialogProvider: DialogProvider
) : ScreenModel {

    private val _articles = mutableStateListOf<Article>()
    val articles: List<Article> = _articles

    private val _uiState = MutableStateFlow<FeedUIState>(FeedUIState.Loading)
    val uiState: StateFlow<FeedUIState> = _uiState.asStateFlow()

    private val _allCountries = MutableStateFlow<List<Country>?>(null)
    val allCountries: StateFlow<List<Country>?> = _allCountries.asStateFlow()

    var selectedCategory: String? = null

    init {
        screenModelScope.launch {
            dataStoreRepository.isFirstLaunch().collect { isFirstLaunch ->
                if (!isFirstLaunch) {
                    dataStoreRepository.getSelectedCountryCode().collect { countryCode ->
                        initializeCountriesList(countryCode)
                        fetchArticles(countryCode)
                    }
                    return@collect
                }

                dataStoreRepository.saveFirstLaunch()
                permissionsManager.requestLocationPermissions(
                    this,
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
                    },
                    onAlwaysDenied = {
                        //TODO this should be called from Composable
//                        dialogProvider.showDialog(
//                            "Location permission denied",
//                            "Please enable location permissions in settings for better experience"
//                        ) {
//                            Logger.d("Location permission denied always")
//                        }

                        Logger.d("Location permission denied always")
                    }
                )
            }
        }
    }

    fun onCategoryClick(category: Category) {
        selectedCategory = category.name
        screenModelScope.launch {
            dataStoreRepository.getSelectedCountryCode().collect { countryCode ->
                fetchArticles(countryCode, category)
            }
        }
    }

    private suspend fun initializeCountriesList(selectedCountryCode: String) {
        when (val result = countriesRepository.getCountries()) {
            is Result.Success -> {
                _allCountries.value = result.data.map { country ->
                    if (country.code == selectedCountryCode) {
                        country.copy(selected = true)
                    } else {
                        country
                    }
                }
            }

            is Result.Error -> Logger.e(
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

    private suspend fun getCurrentLocation(onLocationReceived: suspend (LocationData) -> Unit) {
        locationProvider.getCurrentLocation().collect { location ->
            location?.let {
                onLocationReceived(it)
            }
        }
    }


    //TODO - country code is optional and should be removed from the initial call
    private suspend fun fetchArticles(country: String? = null, category: Category? = null) {
        _uiState.value = FeedUIState.Loading
        _uiState.value =
            when (val result = newsRepository.getNewsArticles(country, category?.name)) {
                is Result.Success -> {
                    _articles.clear()
                    _articles.addAll(result.data)
                    if (result.data.isNotEmpty()) {
                        FeedUIState.Feed(
                            articles = articles,
                            categories = CATEGORIES.map { categoryString ->
                                Category(
                                    name = categoryString,
                                    selected = selectedCategory == categoryString
                                )
                            })
                    } else {
                        FeedUIState.Empty
                    }
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
