package nick.mirosh.newsappkmp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.home
import kotlinproject.composeapp.generated.resources.saved
import nick.mirosh.newsappkmp.ui.favorite.FavoriteScreenVoyager
import nick.mirosh.newsappkmp.ui.feed.FeedScreenVoyager
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeNavigation() {
    TabNavigator(
        HomeTab,
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(HomeTab, FavoriteTab)
            )
        }
    ) {
        var countriesClicked by remember { mutableStateOf(false) }
        if (countriesClicked) {
            Dialog(onDismissRequest = { countriesClicked = false }) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp).background(Color.Green)) {
                    Text("Hello, World!")
                }
            }
        }
        Scaffold(
            topBar = {
                Box {
                    Text(modifier = Modifier.clickable {
                        countriesClicked = !countriesClicked
                    }, text = "balls")
                }

            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    CurrentTab()
                }
            },
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.White,
                    elevation = 8.dp
                ) {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(FavoriteTab)
                }
            }
        )
    }

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}

object FavoriteTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.saved)
            val icon = rememberVectorPainter(Icons.Default.FavoriteBorder)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(FavoriteScreenVoyager())
    }
}

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home)
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(FeedScreenVoyager())
    }
}


class CountriesDialog : Screen {
    override val key: String = "countries"

    @Composable
    override fun Content() {
        Dialog(onDismissRequest = { }) {
            Text("Hello, World!")
        }
    }
}
