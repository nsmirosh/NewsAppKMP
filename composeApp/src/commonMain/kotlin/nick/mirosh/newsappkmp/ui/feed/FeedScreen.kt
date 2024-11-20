package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import nick.mirosh.newsapp.domain.feed.model.Article
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedScreen(
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FeedScreenContent(
        uiState = uiState,
        onArticleClick = { onArticleClick(it) },
        onSavedArticlesClicked = { /*onSavedArticlesClicked()*/ },
        modifier = modifier
    )
}

@Composable
fun FeedScreenContent(
    uiState: FeedUIState,
    onArticleClick: (Article) -> Unit,
    onSavedArticlesClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
                FeedList(
                    modifier = Modifier.padding(16.dp),
                    articles = articles,
                    onArticleClick = onArticleClick
                )
            },
        )
    }
}


@Composable
fun FeedList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier,
//        modifier = modifier.padding(it)
    ) {
        items(articles, key = { article -> article.url }) { article ->
            ArticleItem(
                article = article,
                onArticleClick = onArticleClick,
//                            onLikeClick = onLikeClick
            )
        }
    }
}


@Composable
fun SaveButton(
    liked: Boolean,
    onLikeCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onLikeCLick()
        },
    ) {
        Icon(
            imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Save For Later",
            tint = if (liked) Color.Red else Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
//    onLikeClick: (Article) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .testTag("article_item"),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = article.title,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )

            if (article.urlToImage.isNotEmpty()) {
                val imageModifier = Modifier
                    .clickable { onArticleClick(article) }
                    .width(150.dp)
                    .padding(start = 16.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                //If we're not in Preview mode
                if (!LocalInspectionMode.current) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier,
                        model = article.urlToImage,
                        contentDescription = "Article image"
                    )
                } else {
                    //For Preview mode
                    Image(
                        modifier = imageModifier,
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = "Article image",
                    )
                }

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = article.author,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            SaveButton(
                liked = article.liked,
                onLikeCLick = { /*onLikeClick(article)*/ }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}
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