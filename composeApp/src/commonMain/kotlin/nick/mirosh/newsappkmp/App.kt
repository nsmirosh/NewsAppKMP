package nick.mirosh.newsappkmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.ui.feed.FeedUIState
import nick.mirosh.newsappkmp.ui.feed.FeedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    println("invoking App")
    MaterialTheme {
        FeedScreen(onArticleClick = {}, onSavedArticlesClicked = {})
    }
}

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel(),
    onArticleClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    println("collecting state")
    with(uiState) {
        when (this) {
            is FeedUIState.Feed ->
                ArticleFeed(
                    modifier = modifier,
                    articles = articles,
                    onArticleClick = onArticleClick,
                    onSavedArticlesClicked = onSavedArticlesClicked
                )

            else -> {

            }

        }
    }
}

@Composable
fun ArticleFeed(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
//    onLikeClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Scaffold(
            modifier = modifier,
            content = {
                LazyColumn(
                    modifier = modifier.padding(it)
                ) {
                    items(articles, key = { article -> article.url }) { article ->
                        ArticleItem(
                            article = article,
                            onArticleClick = onArticleClick,
//                            onLikeClick = onLikeClick
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onSavedArticlesClicked,
                    modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)
                ) {
//                    Icon(
//                        modifier = Modifier.size(32.dp),
//                        imageVector = ImageVector.vectorResource(id = R.drawable.save),
//                        contentDescription = "Save"
//                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
        )
    }
}


@Composable
fun LikeButton(
    liked: Boolean,
    modifier: Modifier = Modifier,
    onLikeCLick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onLikeCLick()
        },
    ) {
        Icon(
            imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (liked) Color.Red else Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (Article) -> Unit,
//    onLikeClick: (Article) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .testTag("article_item"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clickable { onArticleClick(article) }
                .width(150.dp)
                .height(150.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            model = article.urlToImage,
            contentDescription = "Article image"
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = article.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))
            LikeButton(liked = article.liked) {
//                onLikeClick(article)
            }
        }
    }
}

//@Composable
//fun NoNetworkState() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Image(
//            modifier = Modifier
//                .testTag("no_network_connection_image")
//                .padding(horizontal = 54.dp),
//            painter = painterResource(id = R.drawable.wifi),
//            contentDescription = "Network connection",
//            alignment = Alignment.Center
//        )
//        Spacer(modifier = Modifier.height(24.dp))
//        Text(
//            modifier = Modifier
//                .padding(start = 32.dp, end = 32.dp, top = 16.dp, bottom = 0.dp),
//            text = stringResource(R.string.no_network_connection),
//            textAlign = TextAlign.Center,
//            fontSize = 18.sp
//        )
//    }
//}

//@Preview
//@Composable
//fun NoNetworkStatePreview() {
//    NewsAppTheme {
//        NoNetworkState()
//    }
//}
