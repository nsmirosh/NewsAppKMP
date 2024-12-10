package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryDTO (
    val name: String? = null,
    @SerialName("alpha-2")
    val code: String? = null
)