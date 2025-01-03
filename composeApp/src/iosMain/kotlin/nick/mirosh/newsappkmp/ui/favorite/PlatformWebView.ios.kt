package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import co.touchlab.kermit.Logger
import nick.mirosh.newsappkmp.ui.feed.NativeLoader
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

@Composable
actual fun PlatformWebView(url: String, modifier: Modifier) {

    var isLoading by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        UIKitView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                WKWebView().apply {
                    navigationDelegate = MyDelegate {
                        isLoading = false
                    }
                    scrollView.setScrollEnabled(true)
                    loadRequest(NSURLRequest(NSURL(string = url)))
                    isLoading = true
                }
            },
            update = { webView ->
                // Update logic if needed
            }
        )

        if (isLoading) {
            NativeLoader(modifier = Modifier.align(Alignment.Center))
        }
    }
}


class MyDelegate(
    private val onFinishedLoading: () -> Unit
) : NSObject(), WKNavigationDelegateProtocol {
    override fun webView(
        webView: WKWebView,
        didFinishNavigation: WKNavigation?
    ) {
        Logger.d("didFinishNavigation")
        onFinishedLoading()
    }

    override fun webView(
        webView: WKWebView,
        didFailNavigation: WKNavigation?,
        withError: NSError
    ) {
        Logger.d("didFailNavigation")
        onFinishedLoading()
    }
}


