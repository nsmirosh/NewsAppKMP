package nick.mirosh.newsappkmp

import androidx.compose.ui.window.ComposeUIViewController
import dev.icerock.moko.permissions.PermissionsController
import nick.mirosh.newsappkmp.di.iOSModule
import nick.mirosh.newsappkmp.di.KoinContainer
import nick.mirosh.newsappkmp.ui.HomeNavigation

fun MainViewController() = ComposeUIViewController {
    val koinApp = KoinContainer.initKoin(platformSpecificModule = iOSModule)
    // Eagerly initialize PermissionsController
    koinApp?.koin?.get<PermissionsController>()
    HomeNavigation()
}