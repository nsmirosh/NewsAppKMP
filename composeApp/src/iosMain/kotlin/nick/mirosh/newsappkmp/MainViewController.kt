package nick.mirosh.newsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.feed.FeedScreenVoyager

fun MainViewController() = ComposeUIViewController {
    KoinContainer.initKoin()
    Navigator(FeedScreenVoyager())
}