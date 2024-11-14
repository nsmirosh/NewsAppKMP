package nick.mirosh.newsappkmp.di

import nick.mirosh.newsapp.domain.feed.usecase.FetchArticlesUsecase
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.ui.feed.FeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factory { FetchArticlesUsecase(get()) }
    single { FetchArticlesUsecase(get()) }
    viewModel { FeedViewModel(get()) }
    single { NewsRemoteDataSource(get()) }
    single { NewsRepositoryImpl(get()) } bind NewsRepository::class
}
