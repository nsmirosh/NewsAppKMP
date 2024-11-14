package nick.mirosh.newsapp.di

//import androidx.room.Room
//import nick.mirosh.newsapp.data.database.AppDatabase
//import nick.mirosh.newsapp.data.database.ArticleDao
//import nick.mirosh.newsapp.data.database.DATABASE_NAME
//import nick.mirosh.newsapp.data.repository.NewsRemoteDataSource
//import nick.mirosh.newsapp.data.repository.NewsRepositoryImpl
//import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
//import nick.mirosh.newsapp.domain.mapper.news.DTOtoDatabaseArticleMapper
//import nick.mirosh.newsapp.domain.mapper.news.DatabaseToDomainArticleMapper
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.module.dsl.bind
//import org.koin.core.module.dsl.factoryOf
//import org.koin.core.module.dsl.singleOf
//import org.koin.dsl.module
//
//
//val repositoryModule = module {
//
//    single<ArticleDao> {
//        Room
//            .databaseBuilder(
//                androidContext(),
//                AppDatabase::class.java,
//                DATABASE_NAME
//            )
//            .build()
//            .articleDao()
//    }
//    factoryOf(::DatabaseToDomainArticleMapper)
//    factoryOf(::DTOtoDatabaseArticleMapper)
//    singleOf(::NewsRemoteDataSource)
//    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }
//}