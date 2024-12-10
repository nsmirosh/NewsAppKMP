package nick.mirosh.newsappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nick.mirosh.newsappkmp.domain.feed.model.Country

@Serializable
data class CountryDTO (
    val name: String? = null,
    @SerialName("alpha-2")
    val code: String? = null
) {
    fun toDomain() = Country(
        name = name.orEmpty(),
        code = code.orEmpty()
    )
}