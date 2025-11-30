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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import completekmpcourseapp.composeapp.generated.resources.Res
import completekmpcourseapp.composeapp.generated.resources.back
import nick.mirosh.newsappkmp.ui.favorite.PlatformWebView
import nick.mirosh.newsappkmp.ui.feed.NativeLoader
import org.jetbrains.compose.resources.painterResource

@Composable
fun DetailsScreen(
    url: String,
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
                    url = url,
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
