package nick.mirosh.newsappkmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import nick.mirosh.newsappkmp.ui.feed.FeedScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    println("invoking App")
    MaterialTheme {
        FeedScreen()
    }
}

