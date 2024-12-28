package nick.mirosh.newsappkmp.data.repository

import nick.mirosh.newsappkmp.data.model.ArticleDTO

interface NewsRemoteDataSource {
    suspend fun getHeadlines(country: String?, category: String?): List<ArticleDTO>
}