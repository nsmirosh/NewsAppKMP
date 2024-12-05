package nick.mirosh.newsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import nick.mirosh.newsapp.di.iOSModule
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.HomeNavigation

fun MainViewController() = ComposeUIViewController {
    KoinContainer.initKoin(platformSpecificModule = iOSModule)
    HomeNavigation()
}