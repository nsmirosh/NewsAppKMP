package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.UIKitView
import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nick.mirosh.newsappkmp.ui.feed.NativeLoader
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun PlatformWebView(url: String, modifier: Modifier) {
    val rememberCoroutineScope = rememberCoroutineScope()
    //TODO see why it's not scrolling here - https://github.com/KevinnZou/compose-webview-multiplatform/blob/b740b9bb126a55fc6083234103d65832dd3fd37f/webview/src/iosMain/kotlin/com/multiplatform/webview/web/WebView.ios.kt#L19

    var isLoading by remember { mutableStateOf(true) }
    Box(modifier = modifier.fillMaxSize()) {
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WKWebView().apply {
                    val nsurl = NSURL(string = url)
                    scrollView.setScrollEnabled(true)
                    loadRequest(NSURLRequest(nsurl))
//                    rememberCoroutineScope.launch {
//                        while (isLoading) {
//                            isLoading = isLoading()
//                            Logger.d("Loading state: $isLoading")
//                            delay(100)
//                        }
//                    }
                }
            },
            update = { webView ->
                // Update logic if needed
            }
        )
        if (isLoading) NativeLoader(modifier = Modifier.align(Alignment.Center))
    }
}
