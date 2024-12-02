package nick.mirosh.newsapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import nick.mirosh.newsapp.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(OkHttp)
}

val androidModule = module {
    factory<RoomDatabase.Builder<AppDatabase>> {
        val dbFile = androidContext().getDatabasePath("my_room.db")
        Room.databaseBuilder<AppDatabase>(
            context =androidContext(),
            name = dbFile.absolutePath
        )
    }

    single<HttpClient> {
        HttpClient(OkHttp)
    }


}