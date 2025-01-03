package nick.mirosh.newsappkmp.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIActivityIndicatorView
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@Composable
actual fun NativeLoader(
    modifier: Modifier
) {
//    IOSWebView()
    UIKitView(
        factory = {
            UIActivityIndicatorView().apply {
                startAnimating()
            }
        },
        modifier = modifier.width(40.dp).height(40.dp)
    )
}


//class IOSWebViewProvider(private val webView: WKWebView) : WebViewProvider {
//    override fun loadUrl(url: String) {
//        webView.loadRequest(NSURLRequest(NSURL(string = url)))
//    }
//}