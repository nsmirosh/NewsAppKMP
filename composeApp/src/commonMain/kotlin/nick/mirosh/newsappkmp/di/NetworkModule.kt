package nick.mirosh.newsappkmp.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import nick.mirosh.newsappkmp.BuildKonfig
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
            install(Resources)

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {

                url {
                    protocol = URLProtocol.HTTPS
                    host = "newsdata.io"
                    path("api/1/")
                    parameters.append("apikey", BuildKonfig.API_KEY)
                }
            }
        }
    }
}


expect fun createPlatformHttpClient(): HttpClient
