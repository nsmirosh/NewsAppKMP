package nick.mirosh.newsappkmp.di

import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.data.database.ArticleDaoConfiguration
import nick.mirosh.newsappkmp.domain.favorite.FetchFavoriteArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesScreenModel
import nick.mirosh.newsappkmp.ui.feed.FeedScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {

    factoryOf(::FetchArticlesUsecase)
    factoryOf(::LikeArticleUsecase)
    factoryOf(::FetchFavoriteArticlesUsecase)

    factoryOf(::FeedScreenModel)
    factoryOf(::FavoriteArticlesScreenModel)

    single<ArticleDao> { ArticleDaoConfiguration(get()).build() }
    single<Json> { Json { ignoreUnknownKeys = true } }
}


