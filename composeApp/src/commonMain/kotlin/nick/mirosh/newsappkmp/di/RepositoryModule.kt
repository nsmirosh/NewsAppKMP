package nick.mirosh.newsappkmp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

import nick.mirosh.newsappkmp.data.repository.CountriesRepositoryImpl
import nick.mirosh.newsappkmp.data.repository.DataStoreRepositoryImpl
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSourceImpl
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsappkmp.domain.feed.repository.CountriesRepository
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named

val repositoryModule = module {

    singleOf(::NewsRemoteDataSourceImpl) { bind<NewsRemoteDataSource>() }
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }
    singleOf(::DataStoreRepositoryImpl) { bind<DataStoreRepository>() }
    single<CountriesRepository> { CountriesRepositoryImpl(get(), get(named("io"))) }
}
val dispatchersModule = module {
    single<CoroutineDispatcher>(named("io")) { Dispatchers.IO }
    single<CoroutineDispatcher>(named("default")) { Dispatchers.Default }
}