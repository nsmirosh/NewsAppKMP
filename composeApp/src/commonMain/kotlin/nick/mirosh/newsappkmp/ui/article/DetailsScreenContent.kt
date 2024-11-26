package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import nick.mirosh.newsapp.domain.feed.model.Article

//voyager Details Screen
class DetailsScreen(private val article: Article) : Screen {
    @Composable
    override fun Content() {
        DetailsScreenContent(article)
    }
}

@Composable
fun DetailsScreenContent(
    article: Article,
    modifier: Modifier = Modifier,
) {
    val state = rememberWebViewState(article.url)
    WebView(state = state, modifier = modifier.fillMaxSize())
}



