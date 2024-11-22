package nick.mirosh.newsappkmp.ui.article

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.model.Source


@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    val article = Article(
        title = "Pope calls for investigation on whether genocide is taking place in Gaza - The Washington Post",
        description = "Description 1",
        url = "url1",
        urlToImage = "",
        liked = false,
        source = Source("id", "name"),
        author = "Anthony Faiola, Niha Masih",
        content = "content",
        publishedAt = "publishedAt",
    )
    DetailsScreenContent(article = article)
}