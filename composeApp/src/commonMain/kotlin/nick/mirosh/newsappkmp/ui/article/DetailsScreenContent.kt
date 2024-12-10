package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.back
import nick.mirosh.newsapp.domain.feed.model.Article
import org.jetbrains.compose.resources.painterResource

class DetailsScreen(private val article: Article) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        DetailsScreenContent(article = article, onBackClick = {
            navigator.pop()
        })
    }
}

@Composable
fun DetailsScreenContent(
    article: Article,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val state = rememberWebViewState(article.url)
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(Res.drawable.back),
                            tint = Color.DarkGray,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {
            WebView(state = state, modifier = modifier.fillMaxSize())
        }
    )
}
