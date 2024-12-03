package nick.mirosh.newsappkmp.ui.favorite


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.ui.article.DetailsScreen
import nick.mirosh.newsappkmp.ui.feed.FeedList


class FavoriteScreenVoyager : Screen {

    override val key = "favorite"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<FavoriteArticlesScreenModel>()
        FavoriteArticlesScreenContent(
            viewModel = screenModel,
            onArticleClick = {
                navigator.push(
                    DetailsScreen(it)
                )
            },
        )
    }
}

@Composable
fun FavoriteArticlesScreenContent(
    viewModel: FavoriteArticlesScreenModel,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier) {
        when (uiState) {
            is FavoriteArticlesUIState.FavoriteArticles ->
                FeedList(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    articles = (uiState as FavoriteArticlesUIState.FavoriteArticles).articles,
                    onArticleClick = onArticleClick
                )

            is FavoriteArticlesUIState.Loading -> {}
//                LoadingProgressBar()

            is FavoriteArticlesUIState.Failed -> {}
//                FailedMessage(message = "Could not load favorite articles")

            is FavoriteArticlesUIState.FavoriteArticlesEmpty -> NoArticles()
        }
    }
}

@Composable
fun NoArticles(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No saved articles",
            fontSize = 24.sp,
        )
    }
}
