package nick.mirosh.newsappkmp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nick.mirosh.newsappkmp.domain.feed.repository.DataStoreRepository
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "dice.preferences_pb"


const val DEFAULT_COUNTRY_CODE = "US"
const val COUNTRY_CODE = "country_code_key"
const val IS_FIRST_LAUNCH = "is_first_launch_key"

class DataStoreRepositoryImpl(private val prefs: DataStore<Preferences>) : DataStoreRepository {

    override suspend fun saveSelectedCountryCode(value: String) {
        prefs.edit {
            it[stringPreferencesKey(COUNTRY_CODE)] = value
        }
    }

    override fun getSelectedCountryCode() = prefs.data.map {
        it[stringPreferencesKey(COUNTRY_CODE)] ?: DEFAULT_COUNTRY_CODE
    }

    override suspend fun saveFirstLaunch() {
        prefs.edit {
            it[booleanPreferencesKey(IS_FIRST_LAUNCH)] = false
        }
    }

    override fun isFirstLaunch() = prefs.data.map {
        it[booleanPreferencesKey(IS_FIRST_LAUNCH)] ?: true
    }
}