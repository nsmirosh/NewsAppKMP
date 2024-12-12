package nick.mirosh.newsappkmp.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.data.database.AppDatabase
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


