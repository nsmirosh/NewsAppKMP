package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import coil3.compose.AsyncImage
import nick.mirosh.newsapp.domain.feed.model.Article


class FavoriteScreenVoyager : Screen {

    override val key = "favorite"

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<FavoriteArticlesScreenModel>()
        FavoriteArticlesScreenContent(screenModel)
    }
}

@Composable
fun FavoriteArticlesScreenContent(
    viewModel: FavoriteArticlesScreenModel,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box (modifier = modifier){
        when (uiState) {
            is FavoriteArticlesUIState.FavoriteArticles ->
                Articles(
                    articles = (uiState as FavoriteArticlesUIState.FavoriteArticles).articles,
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
fun Articles(
    modifier: Modifier = Modifier,
    articles: List<Article>
) {
    LazyColumn(modifier = modifier) {
        items(articles.size) { index ->
            val article = articles[index]
            Row(
                modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .height(150.dp)
                        .padding(8.dp)
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),

                    model = article.urlToImage,
                    contentDescription = "Translated description of what the image contains"
                )
                Text(

                    text = article.title,
                    lineHeight = 18.sp,
                    fontSize = 14.sp

                )
            }
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
