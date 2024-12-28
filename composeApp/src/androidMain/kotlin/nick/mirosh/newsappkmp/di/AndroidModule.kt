package nick.mirosh.newsappkmp.di

import androidx.activity.ComponentActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.icerock.moko.permissions.PermissionsController
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsappkmp.MainActivity
import nick.mirosh.newsappkmp.data.createDataStore
import nick.mirosh.newsappkmp.data.database.DATABASE_NAME
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.LocationProviderImpl
import nick.mirosh.newsappkmp.location.ReverseGeocodingService
import nick.mirosh.newsappkmp.location.ReverseGeocodingServiceImpl
import nick.mirosh.newsappkmp.ui.utils.AndroidDialogProvider
import nick.mirosh.newsappkmp.ui.utils.DialogProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(OkHttp)
}

val androidModule = module {

    single<PermissionsController> { (activity: ComponentActivity) ->
        PermissionsController(
            androidContext()
        ).also {
            it.bind(activity)
        }
    }

    single<RoomDatabase.Builder<AppDatabase>> {
        val dbFile = androidContext().getDatabasePath(DATABASE_NAME)
        Room.databaseBuilder<AppDatabase>(
            context = androidContext(),
            name = dbFile.absolutePath
        )
    }

    single { LocationProviderImpl(androidContext()) } bind LocationProvider::class
    single { ReverseGeocodingServiceImpl(androidContext()) } bind ReverseGeocodingService::class

    single<DataStore<Preferences>> { createDataStore(androidContext()) }

    single<HttpClient> { HttpClient(OkHttp) }

//    factory { (activity: ComponentActivity) -> AndroidDialogProvider(activity) } bind DialogProvider::class

//    scope<MainActivity> {
//        scoped { AndroidDialogProvider(androidContext()) } bind DialogProvider::class
//    }
}



