package nick.mirosh.newsappkmp.data.repository

import co.touchlab.kermit.Logger
import kotlinproject.composeapp.generated.resources.Res
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.data.model.CountryDTO
import nick.mirosh.newsappkmp.domain.feed.repository.CountriesRepository
import org.jetbrains.compose.resources.ExperimentalResourceApi

const val jsonFileName = "countries.json"

class CountriesRepositoryImpl(
    private val json: Json,
    private val ioDispatcher: CoroutineDispatcher
) : CountriesRepository {

    @OptIn(ExperimentalResourceApi::class, ExperimentalStdlibApi::class)
    override suspend fun getCountries() =
        try {
            val dtos = withContext(ioDispatcher) {
                val dispatcher = coroutineContext[CoroutineDispatcher]?.toString() ?: "Unknown Dispatcher"
                Logger.d("Reading countries from resources in dispatcher with CoroutineDispatcher: $dispatcher")
                val readBytes = Res.readBytes("files/$jsonFileName")
                val jsonData = readBytes.decodeToString()
                json.decodeFromString<List<CountryDTO>>(jsonData)
            }
            Result.Success(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.e("Error reading countries", e)
            Result.Error(e)
        }
}
