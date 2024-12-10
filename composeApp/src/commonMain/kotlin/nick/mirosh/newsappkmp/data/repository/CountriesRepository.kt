package nick.mirosh.newsappkmp.data.repository

import co.touchlab.kermit.Logger
import kotlinproject.composeapp.generated.resources.Res
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.data.model.CountryDTO
import org.jetbrains.compose.resources.ExperimentalResourceApi

const val jsonFileName = "countries.json"

class CountriesRepository(
    private val json: Json
) {

    val countries: Flow<Result<List<CountryDTO>>> = parseCountries()

    @OptIn(ExperimentalResourceApi::class)
    private fun parseCountries() = flow {
        emit(
            try {
                val readBytes = Res.readBytes("files/$jsonFileName")
                val jsonData = readBytes.decodeToString()
                Result.Success(json.decodeFromString<List<CountryDTO>>(jsonData))
            } catch (e: Exception) {
                e.printStackTrace()
                Logger.e("Error reading countries", e)
                Result.Error(e)
            }
        )
    }
}
