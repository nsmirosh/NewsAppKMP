package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    @SerialName("results")
    val articles: List<ArticleDTO>? = null
)
