package nick.mirosh.newsappkmp.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import nick.mirosh.newsappkmp.data.model.ApiResponse
import nick.mirosh.newsappkmp.data.model.ArticleDTO

const val ENDPOINT = "api/1/latest"
const val COUNTRY_PARAM = "country"

class NewsRemoteDataSourceImpl(private val client: HttpClient): NewsRemoteDataSource {
    override suspend fun getHeadlines(country: String): List<ArticleDTO> {
        return client.get {
            url {
                path(ENDPOINT)
                parameters.append(COUNTRY_PARAM, country)
            }
        }.body<ApiResponse>().articles!!
    }
}