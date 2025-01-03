package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
expect fun PlatformWebView(url: String, modifier: Modifier)

