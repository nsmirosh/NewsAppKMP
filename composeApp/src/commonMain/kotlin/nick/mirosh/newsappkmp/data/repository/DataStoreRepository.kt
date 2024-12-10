package nick.mirosh.newsappkmp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "dice.preferences_pb"


const val DEFAULT_COUNTRY = "USA"
const val COUNTRY_KEY = "country_key"

class DataStoreRepositoryImpl(private val prefs: DataStore<Preferences>) : DataStoreRepository {

    override suspend fun saveCountry(value: String) {
        prefs.edit {
            it[stringPreferencesKey(COUNTRY_KEY)] = value
        }
    }

    override fun getCountry() = prefs.data.map {
        it[stringPreferencesKey(COUNTRY_KEY)] ?: DEFAULT_COUNTRY
    }
}