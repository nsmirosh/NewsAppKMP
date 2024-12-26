package nick.mirosh.newsappkmp.di

import nick.mirosh.newsappkmp.data.manager.PermissionManager
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesScreenModel
import nick.mirosh.newsappkmp.ui.feed.FeedScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {

    //screenModels
    factoryOf(::FeedScreenModel)
    factoryOf(::FavoriteArticlesScreenModel)

    factoryOf(::PermissionManager)
}


