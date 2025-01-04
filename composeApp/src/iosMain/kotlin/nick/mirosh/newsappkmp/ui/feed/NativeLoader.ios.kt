package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import platform.UIKit.UIActivityIndicatorView

@Composable
actual fun NativeLoader(
    modifier: Modifier
) {
    UIKitView(
        factory = {
            UIActivityIndicatorView().apply {
                startAnimating()
            }
        },
        modifier = modifier.width(40.dp).height(40.dp)
    )
}

