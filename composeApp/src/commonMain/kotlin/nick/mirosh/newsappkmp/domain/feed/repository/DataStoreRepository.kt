package nick.mirosh.newsappkmp.domain.feed.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveCountry(value: String)
    fun getCountry(): Flow<String?>
}