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
import completekmpcourseapp.composeapp.generated.resources.Res
import completekmpcourseapp.composeapp.generated.resources.no_saved_articles
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.ui.feed.FeedList
import org.jetbrains.compose.resources.stringResource

@Composable
fun FavoriteScreen(
    viewModel: FavoriteArticlesViewModel,
    onArticleClick: (Article) -> Unit,
) {
    FavoriteArticlesScreenContent(
        viewModel = viewModel,
        onArticleClick = onArticleClick
    )
}

@Composable
fun FavoriteArticlesScreenContent(
    viewModel: FavoriteArticlesViewModel,
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

            is FavoriteArticlesUIState.Failed -> {}

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
            text = stringResource(Res.string.no_saved_articles),
            fontSize = 24.sp,
        )
    }
}
