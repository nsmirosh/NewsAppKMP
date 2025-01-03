package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformWebView(
    url: String,
    startedLoading: () -> Unit,
    finishedLoading: () -> Unit,
    modifier: Modifier = Modifier
)

