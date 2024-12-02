package nick.mirosh.newsappkmp.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.domain.feed.usecase.LikeArticleUsecase
import nick.mirosh.newsappkmp.ui.feed.FeedScreenModel
import nick.mirosh.newsappkmp.ui.feed.FeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single { FetchArticlesUsecase(get()) }
    single { LikeArticleUsecase(get()) }
    viewModel { FeedViewModel(get(), get()) }
    single { NewsRemoteDataSource(get()) }
    single { NewsRepositoryImpl(get(), get()) } bind NewsRepository::class


    //voyager
    factory { FeedScreenModel(get(), get()) }

    single<ArticleDao> { ArticleDaoConfiguration(get()).build() }

//    fun getRoomDatabase(
//        builder: RoomDatabase.Builder<AppDatabase>
//    ): ArticleDao {
//        return builder
//            .setDriver(BundledSQLiteDriver())
//            .setQueryCoroutineContext(Dispatchers.IO)
//            .build()
//            .articleDao()
//    }


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


interface DatabaseProvider {
    fun getDatabaseBuilder(ctx: Any? = null): RoomDatabase.Builder<AppDatabase>
}
