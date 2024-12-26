package nick.mirosh.newsappkmp.di

import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsappkmp.data.database.ArticleDaoConfiguration
import org.koin.dsl.module

val dataModule = module{

    single<ArticleDao> { ArticleDaoConfiguration(get()).build() }
    single<Json> { Json { ignoreUnknownKeys = true } }
}