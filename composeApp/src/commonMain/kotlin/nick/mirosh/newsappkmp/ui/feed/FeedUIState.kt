package nick.mirosh.newsappkmp.ui.feed

import androidx.annotation.StringDef
import nick.mirosh.newsapp.domain.feed.model.Article
import org.jetbrains.compose.resources.StringResource

sealed class FeedUIState {
    data class Feed(val articles: List<Article>) : FeedUIState()
    data object Empty : FeedUIState()
//    data object NoNetworkConnection : FeedUIState()
    data object Loading : FeedUIState()
    data object FetchingArticlesFailed : FeedUIState()
    data class Error(val error: StringResource) : FeedUIState()
}

