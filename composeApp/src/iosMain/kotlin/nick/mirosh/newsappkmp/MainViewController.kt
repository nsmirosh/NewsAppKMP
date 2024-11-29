package nick.mirosh.newsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.InitialContent

fun MainViewController() = ComposeUIViewController {
    KoinContainer.initKoin()
    InitialContent()
}