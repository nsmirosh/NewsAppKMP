package nick.mirosh.newsappkmp.data.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import nick.mirosh.newsapp.data.database.AppDatabase

class ArticleDaoConfiguration(
    private val builder: RoomDatabase.Builder<AppDatabase>
) {
    fun build() = builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
        .articleDao()
}
