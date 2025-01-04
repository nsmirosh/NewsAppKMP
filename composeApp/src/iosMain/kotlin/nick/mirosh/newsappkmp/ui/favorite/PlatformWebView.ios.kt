package nick.mirosh.newsappkmp.ui.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.darwin.NSObject

@Composable
actual fun PlatformWebView(
    url: String,
    startedLoading: () -> Unit,
    finishedLoading: () -> Unit,
    modifier: Modifier
) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            WKWebView().apply {
                navigationDelegate = NavigationDelegate {
                    finishedLoading()
                }
                scrollView.setScrollEnabled(true)
            }
        },
        update = { webView ->
            webView.loadRequest(NSURLRequest(NSURL(string = url)))
            startedLoading()
        }
    )
}


class NavigationDelegate(
    private val onFinishedLoading: () -> Unit
) : NSObject(), WKNavigationDelegateProtocol {
    override fun webView(
        webView: WKWebView,
        didFinishNavigation: WKNavigation?
    ) {
        onFinishedLoading()
    }

    override fun webView(
        webView: WKWebView,
        didFailNavigation: WKNavigation?,
        withError: NSError
    ) {
        onFinishedLoading()
    }
}


