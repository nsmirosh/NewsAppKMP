package nick.mirosh.newsappkmp.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import nick.mirosh.newsappkmp.data.model.ApiResponse
import nick.mirosh.newsappkmp.data.model.ArticleDTO

@Resource("/latest")
class Latest(val country: String? = null, val category: String? = null)


class NewsRemoteDataSourceImpl(private val client: HttpClient) : NewsRemoteDataSource {

    override suspend fun getHeadlines(
        country: String?,
        category: String?
    ): List<ArticleDTO> {
        return client.get(Latest(country, category)).body<ApiResponse>().articles!!
    }
}