package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import nick.mirosh.newsapp.domain.feed.model.Article
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun DetailsScreenContent(
    article: Article,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(article.title)
        Text(article.author)
        val imageModifier = Modifier.fillMaxWidth()
        if (!LocalInspectionMode.current) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = imageModifier,
                model = article.urlToImage,
                contentDescription = "Article image"
            )
        } else {
            //For Preview mode
            Image(
                modifier = imageModifier,
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Stub image",
            )
        }

        Text(article.content)
    }
}




