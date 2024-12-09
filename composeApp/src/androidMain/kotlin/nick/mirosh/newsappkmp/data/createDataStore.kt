package nick.mirosh.newsappkmp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import nick.mirosh.newsappkmp.data.repository.dataStoreFileName

fun createDataStore(context: Context): DataStore<Preferences> =
    nick.mirosh.newsappkmp.data.repository.createDataStore(producePath = {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    })