package nick.mirosh.newsappkmp.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.format.DateTimeComponents
import nick.mirosh.newsapp.domain.feed.model.Article
import org.jetbrains.compose.resources.painterResource

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

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = article.author,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            fontSize = 12.sp,
            color = Color.Gray,
            text = formatDateTime(article.publishedAt),
            modifier = Modifier.padding(start = 16.dp)
        )
        val imageModifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        if (!LocalInspectionMode.current) {
            AsyncImage(
                contentScale = ContentScale.Fit,
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

        Text(text = article.content, modifier = Modifier.padding(16.dp))
    }
}

fun formatDateTime(dateString: String) =
    try {
        DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET.parse(dateString).run {
            "${
                month?.name?.lowercase()?.replaceFirstChar { it.uppercase() }
            }, $dayOfMonth, $year $hour:$minute"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }


