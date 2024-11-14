package nick.mirosh.newsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import nick.mirosh.newsappkmp.di.KoinContainer

fun MainViewController() = ComposeUIViewController {
    KoinContainer.initKoin()
    App()
}