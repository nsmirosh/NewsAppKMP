package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.Serializable
import nick.mirosh.newsappkmp.domain.feed.model.Source

@Serializable
data class SourceDTO(
    val id: String? = null,
    val name: String? = null
) {
    fun toSource() = Source(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}
