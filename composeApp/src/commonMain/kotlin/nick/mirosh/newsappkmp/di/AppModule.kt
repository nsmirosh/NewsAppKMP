package nick.mirosh.newsappkmp.di

import nick.mirosh.newsappkmp.data.manager.PermissionManager
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesViewModel
import nick.mirosh.newsappkmp.ui.feed.FeedViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {

    //viewModels
    factoryOf(::FeedViewModel)
    factoryOf(::FavoriteArticlesViewModel)

    factoryOf(::PermissionManager)
}


