package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import completekmpcourseapp.composeapp.generated.resources.Res
import completekmpcourseapp.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.LocalDateTime
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.model.Country
import nick.mirosh.newsappkmp.ui.country.CountryDialog
import nick.mirosh.newsappkmp.ui.theme.DarkGray
import nick.mirosh.newsappkmp.ui.theme.Highlight
import nick.mirosh.newsappkmp.ui.theme.LightGray
import org.jetbrains.compose.resources.painterResource

@Composable
fun FeedScreen(
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val countries by viewModel.allCountries.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                countries = countries,
                onCountryClicked = { viewModel.saveCountry(it) },
                modifier = modifier
            )
        },
        content = { padding ->
            FeedScreenContent(
                uiState = uiState,
                onArticleClick = onArticleClick,
                onLikeClick = { viewModel.onLikeClick(it) },
                onCategoryClicked = { viewModel.onCategoryClick(it) },
                modifier = modifier.padding(padding).fillMaxSize()
            )
        },
    )
}

@Composable
fun TopBar(
    countries: List<Country>? = null,
    onCountryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var countriesClicked by remember { mutableStateOf(false) }
    if (countriesClicked) {
        countries?.let {
            CountryDialog(
                countries = it,
                onCountryClicked = { selectedCountryCode ->
                    onCountryClicked(selectedCountryCode)
                    countriesClicked = false
                },
                onDismissRequest = { countriesClicked = false }
            )
        }
    }

    TopAppBar(
        backgroundColor = Color.White,
        elevation = 8.dp,
    ) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray,
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = { countriesClicked = !countriesClicked },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Select Country",
                        tint = Color.White
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = countries?.first { it.selected }?.name ?: "Select Country"
                    )
                }
            }
        }
    }
}



@Composable
fun FeedScreenContent(
    uiState: FeedUIState,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit,
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        with(uiState) {
            when (this) {
                is FeedUIState.Feed ->
                    Feed(
                        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        articles = articles,
                        categories = categories,
                        onArticleClick = onArticleClick,
                        onLikeClick = onLikeClick,
                        onCategoryClicked = onCategoryClicked
                    )

                is FeedUIState.Loading -> NativeLoader(
                    modifier = Modifier.align(Alignment.Center)
                )

                is FeedUIState.Error -> {

                }

                else -> {

                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Feed(
    articles: List<Article>,
    categories: List<Category>,
    onArticleClick: (Article) -> Unit,
    onLikeClick: (Article) -> Unit,
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CategoriesList(
            categories = categories,
            onCategoryClicked = onCategoryClicked,
        )
        FeedList(
            articles = articles,
            onArticleClick = onArticleClick,
            onLikeClick = onLikeClick,
            modifier = modifier
        )
    }
}

@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .background(
                        color = if (category.selected) Highlight else LightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp)
                    .clickable {
                        onCategoryClicked(category)
                    },
            ) {
                Text(
                    text = category.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = if (category.selected) Color.White else DarkGray,
                    )
                )

            }
        }

    }

}


@Composable
fun FeedList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit,
    onLikeClick: ((Article) -> Unit)? = null,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier,
    ) {
        items(articles, key = { article -> article.url }) { article ->
            ArticleItem(
                article = article,
                onArticleClick = onArticleClick,
                onLikeClick = onLikeClick
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
    onLikeClick: ((Article) -> Unit)? = null
) {
    Column(modifier = modifier.clickable { onArticleClick(article) }) {
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
            Column {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = formatDateTime(article.publishedAt),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                )
                if (article.author.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = article.author,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
            onLikeClick?.let {
                SaveButton(
                    liked = article.liked,
                    onLikeCLick = { onLikeClick(article) }
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

fun formatDateTime(dateString: String) =
    try {
        Ingredient("", "")
        LocalDateTime.parse(dateString.replace(" ", "T")).run {
            "${
                month.name.lowercase().replaceFirstChar { it.uppercase() }
            } $dayOfMonth, $year $hour:${minute.let { if (it > 9) minute else "0$minute" }}"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

data class Ingredient(
    val name: String,
    val quantity: String
)