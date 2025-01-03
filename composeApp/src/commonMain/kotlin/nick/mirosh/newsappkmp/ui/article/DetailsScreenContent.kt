package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import co.touchlab.kermit.Logger
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.back
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val state = rememberWebViewState(article.url)
    var showLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

//    var shouldPoll by remember { mutableStateOf(true) }
//    scope.launch {
//        while (shouldPoll) {
//            Logger.d("Loading state: ${state.loadingState}")
//            if (state.loadingState == LoadingState.Finished) {
//                Logger.d("FINISHED LOADING!!!")
//                shouldPoll = false
//            }
//            delay(20)
//        }
//        showLoading = false
//    }

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
            Box(
                modifier = modifier.fillMaxSize()
            ) {
                /*if (showLoading) {
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        NativeLoader()
                        Text("Loading...")
                    }
                } else {*/
                    PlatformWebView(
                        url = article.url,
                        modifier = modifier.fillMaxSize(),
                    )
//                    WebView(
//                        state = state,
//                        modifier = modifier.fillMaxSize(),
//                    )

                }
//            }
        }
    )
}
