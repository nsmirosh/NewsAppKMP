package nick.mirosh.newsappkmp.ui

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.app_name
import kotlinproject.composeapp.generated.resources.article
import org.jetbrains.compose.resources.StringResource

enum class FeedScreen(val title: StringResource) {
    Start(title = Res.string.app_name),
    ReadArticle(title = Res.string.article),
}
