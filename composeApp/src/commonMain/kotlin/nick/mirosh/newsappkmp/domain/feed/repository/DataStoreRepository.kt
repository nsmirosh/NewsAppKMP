package nick.mirosh.newsappkmp.domain.feed.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveSelectedCountryCode(value: String)
    fun getSelectedCountryCode(): Flow<String>
    suspend fun saveFirstLaunch()
    fun isFirstLaunch(): Flow<Boolean>
}