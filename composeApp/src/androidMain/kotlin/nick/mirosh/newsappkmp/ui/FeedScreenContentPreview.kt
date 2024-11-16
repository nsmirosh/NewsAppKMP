package nick.mirosh.newsappkmp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.model.Source
import nick.mirosh.newsappkmp.ui.feed.FeedList
import nick.mirosh.newsappkmp.ui.feed.FeedScreenContent
import nick.mirosh.newsappkmp.ui.feed.FeedUIState

@Preview(showBackground = true)
@Composable
fun FeedScreenContentPreview() {
    FeedList(
        listOf(
            Article(
                title = "Title 1",
                description = "Description 1",
                url = "url1",
                urlToImage = "",
                liked = false,
                source = Source("id", "name"),
                author = "author",
                content = "content",
                publishedAt = "publishedAt",
            ),
        ),
        onArticleClick = {},
    )
}
