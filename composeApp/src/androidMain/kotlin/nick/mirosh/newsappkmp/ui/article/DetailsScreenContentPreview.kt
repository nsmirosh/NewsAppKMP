package nick.mirosh.newsappkmp.ui.article

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun DetailsScreenContentPreview() {
    DetailsScreen(
        url = "https://example.com/article",
        onBackClick = {}
    )
}