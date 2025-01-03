package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.back
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.ui.favorite.PlatformWebView
import nick.mirosh.newsappkmp.ui.feed.NativeLoader
import org.jetbrains.compose.resources.painterResource

class DetailsScreen(private val article: Article) : Screen {
    @Composable
    override fun Content() {
        NativeLoader()
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
            var isLoading by remember { mutableStateOf(false) }
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                PlatformWebView(
                    url = article.url,
                    startedLoading = { isLoading = true },
                    finishedLoading = { isLoading = false },
                )
                if (isLoading) {
                    NativeLoader(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}
