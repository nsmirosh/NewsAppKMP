package nick.mirosh.newsappkmp.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.data.repository.DataStoreRepositoryImpl
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsappkmp.domain.favorite.FetchFavoriteArticlesUsecase
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesScreenModel
import nick.mirosh.newsappkmp.ui.feed.FeedScreenModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    factory { FetchArticlesUsecase(get()) }
    factory { LikeArticleUsecase(get()) }
    factory { FetchFavoriteArticlesUsecase(get()) }

    single { NewsRemoteDataSource(get()) }
    single { NewsRepositoryImpl(get(), get()) } bind NewsRepository::class
    single { DataStoreRepositoryImpl(get()) } bind DataStoreRepository::class

    factory { FeedScreenModel(get(), get(), get(), get(), get(), get()) }
    factory { FavoriteArticlesScreenModel(get()) }

    single<ArticleDao> { ArticleDaoConfiguration(get()).build() }
}


class ArticleDaoConfiguration(
    private val builder: RoomDatabase.Builder<AppDatabase>
) {
    fun build(): ArticleDao {
        return builder
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
            .articleDao()
    }
}
