package nick.mirosh.newsappkmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nick.mirosh.newsappkmp.ui.FeedScreen
import nick.mirosh.newsappkmp.ui.feed.FeedScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
    navController: NavHostController = rememberNavController()
) {
    MaterialTheme {
        Scaffold(
//            topBar = {
//                CupcakeAppBar(
//                    currentScreen = currentScreen,
//                    canNavigateBack = navController.previousBackStackEntry != null,
//                    navigateUp = { navController.navigateUp() }
//                )
//            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = FeedScreen.Start.name,
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                composable(route = FeedScreen.Start.name) {
                    FeedScreen(onArticleClick = {
                        navController.navigate(FeedScreen.ReadArticle.name)
                    })
                }
                composable(route = FeedScreen.ReadArticle.name) {
                    Text("Read Article")
                }
            }
        }
    }
}

