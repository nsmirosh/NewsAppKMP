package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeLoader(
    modifier: Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}
