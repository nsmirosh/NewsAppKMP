package nick.mirosh.newsapp.di

import org.koin.dsl.module

import nick.mirosh.newsappkmp.data.repository.CountriesRepositoryImpl
import nick.mirosh.newsappkmp.data.repository.DataStoreRepositoryImpl
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSourceImpl
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsappkmp.domain.feed.repository.CountriesRepository
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

val repositoryModule = module {
    singleOf(::NewsRemoteDataSourceImpl)
    singleOf(::NewsRepositoryImpl) { bind<NewsRepository>() }
    singleOf(::DataStoreRepositoryImpl) { bind<DataStoreRepository>() }
    singleOf(::CountriesRepositoryImpl) { bind<CountriesRepository>() }
}