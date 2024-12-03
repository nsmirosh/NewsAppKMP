package nick.mirosh.newsappkmp.ui.favorite

import nick.mirosh.newsapp.domain.feed.model.Article

sealed class FavoriteArticlesUIState {
    data class FavoriteArticles(val articles: List<Article>) : FavoriteArticlesUIState()
    data object FavoriteArticlesEmpty : FavoriteArticlesUIState()
    data object Loading : FavoriteArticlesUIState()
    data object Failed : FavoriteArticlesUIState()
}
