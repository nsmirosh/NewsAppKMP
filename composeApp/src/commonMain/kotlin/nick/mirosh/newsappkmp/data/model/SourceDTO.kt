package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SourceDTO(
    val id: String? = null,
    val name: String? = null
)
