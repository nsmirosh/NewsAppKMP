package nick.mirosh.newsapp.di

import KotlinProject.composeApp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {
    single<HttpClient> {
        createPlatformHttpClient().config {
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "newsapi.org"
                    header("X-Api-Key", BuildConfig.API_KEY)
                }
            }
        }
    }

}





expect fun createPlatformHttpClient(): HttpClient
