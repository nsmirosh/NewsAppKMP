package nick.mirosh.newsapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.icerock.moko.permissions.PermissionsController
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsappkmp.MyViewModel
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.LocationProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
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

    single{ LocationProviderImpl(androidContext())} bind LocationProvider::class
//    single {
//        PermissionsController(androidContext()).bind()
//    }
//    viewModel{
//        MyViewModel()
//    }

    single<HttpClient> {
        HttpClient(OkHttp)
    }



}