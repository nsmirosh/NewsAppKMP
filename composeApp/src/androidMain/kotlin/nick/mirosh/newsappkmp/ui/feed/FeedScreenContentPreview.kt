package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nick.mirosh.newsapp.domain.feed.model.Article

@Preview(showBackground = true)
@Composable
fun FeedScreenContentPreview() {
    Feed(
        modifier = Modifier.padding(16.dp),
        articles = listOf(
            Article(
                title = "Pope calls for investigation on whether genocide is taking place in Gaza - The Washington Post",
                url = "url1",
                urlToImage = "",
                liked = false,
                author = "Anthony Faiola, Niha Masih",
                publishedAt = "2024-11-21T05:37:29Z",
            ),

            Article(
                title = "Earth may have had a Saturn-like ring over 400 million years ago, scientists say - CNN",
                url = "url2",
                urlToImage = "https://i0.wp.com/stratechery.com/wp-content/uploads/2018/03/cropped-android-chrome-512x512-1.png?fit=512%2C512&ssl=1",
                liked = false,
                author = "Taylor Nicioli",
                publishedAt = "2024-11-21T10:47:00Z",
            ),
        ),
        categories = listOf(Category("business"), Category("politics")),
        onArticleClick = {},
        onLikeClick = {},
        onCategoryClicked = {},
    )
}
