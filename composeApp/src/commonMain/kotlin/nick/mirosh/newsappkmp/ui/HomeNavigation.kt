package nick.mirosh.newsappkmp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import completekmpcourseapp.composeapp.generated.resources.Res
import completekmpcourseapp.composeapp.generated.resources.home
import completekmpcourseapp.composeapp.generated.resources.saved
import nick.mirosh.newsappkmp.ui.article.DetailsScreen
import nick.mirosh.newsappkmp.ui.favorite.FavoriteScreen
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesViewModel
import nick.mirosh.newsappkmp.ui.feed.FeedScreen
import nick.mirosh.newsappkmp.ui.feed.FeedViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private object Routes {
    const val Feed = "feed"
    const val Favorite = "favorite"
    const val Details = "details?url={url}"
}

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    Scaffold(
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(navController = navController, startDestination = Routes.Feed) {
                    composable(Routes.Feed) {
                        val vm: FeedViewModel = koinViewModel()
                        FeedScreen(
                            viewModel = vm,
                            onArticleClick = { article ->
                                val encoded = encodeUrl(article.url)
                                navController.navigate("details?url=$encoded")
                            }
                        )
                    }
                    composable(Routes.Favorite) {
                        val vm: FavoriteArticlesViewModel = koinViewModel()
                        FavoriteScreen(
                            viewModel = vm,
                            onArticleClick = { article ->
                                val encoded = encodeUrl(article.url)
                                navController.navigate("details?url=$encoded")
                            }
                        )
                    }
                    composable(Routes.Details) { backStackEntry ->
                        val raw = backStackEntry.arguments?.getString("url") ?: ""
                        val url = decodeUrl(raw)
                        DetailsScreen(
                            url = url,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        },
        bottomBar = {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry?.destination
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = 8.dp
            ) {
                BottomNavItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.Feed } == true,
                    onClick = { navController.navigate(Routes.Feed) },
                    icon = { Icon(painter = rememberVectorPainter(Icons.Default.Home), contentDescription = stringResource(Res.string.home)) }
                )
                BottomNavItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.Favorite } == true,
                    onClick = { navController.navigate(Routes.Favorite) },
                    icon = { Icon(painter = rememberVectorPainter(Icons.Default.FavoriteBorder), contentDescription = stringResource(Res.string.saved)) }
                )
            }
        }
    )
}

@Composable
private fun RowScope.BottomNavItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        icon = icon
    )
}

private fun encodeUrl(url: String): String = url
    .replace("%", "%25")
    .replace("/", "%2F")
    .replace(":", "%3A")
    .replace("?", "%3F")
    .replace("&", "%26")
    .replace("=", "%3D")

private fun decodeUrl(url: String): String = url
    .replace("%3D", "=")
    .replace("%26", "&")
    .replace("%3F", "?")
    .replace("%3A", ":")
    .replace("%2F", "/")
    .replace("%25", "%")