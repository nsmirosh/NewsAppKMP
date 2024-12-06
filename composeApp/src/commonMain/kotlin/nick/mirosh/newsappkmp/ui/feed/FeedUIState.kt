package nick.mirosh.newsappkmp.ui.feed

import nick.mirosh.newsapp.domain.feed.model.Article

sealed class FeedUIState {
    data class Feed(val articles: List<Article>) : FeedUIState()
    data object Empty : FeedUIState()
    data object NoNetworkConnection : FeedUIState()
    data object Loading : FeedUIState()
    data object Failed : FeedUIState()
}

