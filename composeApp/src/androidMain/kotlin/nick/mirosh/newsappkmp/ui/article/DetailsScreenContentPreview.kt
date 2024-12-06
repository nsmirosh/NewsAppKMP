package nick.mirosh.newsappkmp.ui.article

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nick.mirosh.newsapp.domain.feed.model.Article


@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    val article = Article(
        title = "Pope calls for investigation on whether genocide is taking place in Gaza - The Washington Post",
        url = "url1",
        urlToImage = "",
        liked = false,
        author = "Anthony Faiola, Niha Masih",
        publishedAt = "2024-11-21T10:47:00Z",
    )
    DetailsScreenContent(article = article)
}