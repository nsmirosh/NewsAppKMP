package nick.mirosh.newsappkmp.ui.article

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun DetailsScreenContent(
    articleUrl: String,
    modifier: Modifier = Modifier,
) {
    val state = rememberWebViewState(articleUrl)
    WebView(state, modifier)
}

//voyager Details Screen
class DetailsScreen(private val articleUrl: String) : Screen {
    @Composable
    override fun Content() {
        DetailsScreenContent(articleUrl = articleUrl)
    }
}

