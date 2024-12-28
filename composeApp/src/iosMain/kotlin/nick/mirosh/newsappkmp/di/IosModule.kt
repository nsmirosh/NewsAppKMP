package nick.mirosh.newsappkmp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.ios.PermissionsController as PermissionsControllerIOS
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsappkmp.data.database.DATABASE_NAME
import nick.mirosh.newsappkmp.location.IOSLocationProvider
import nick.mirosh.newsappkmp.location.LocationProvider
import nick.mirosh.newsappkmp.location.ReverseGeocodingService
import nick.mirosh.newsappkmp.location.IosReverseGeocodingService
import nick.mirosh.newsappkmp.repository.createIosDataStore
import nick.mirosh.newsappkmp.ui.utils.DialogProvider
import nick.mirosh.newsappkmp.ui.utils.IosDialogProvider
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(Darwin)
}

val iOSModule = module {

//    factory { IosDialogProvider() } bind DialogProvider::class
    factory { HttpClient(Darwin) }

    single { PermissionsControllerIOS() } bind PermissionsController::class

    @OptIn(ExperimentalForeignApi::class)
    fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }

    single {
        val dbFilePath = documentDirectory() + "/$DATABASE_NAME"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }
    single<DataStore<Preferences>> { createIosDataStore() }

    single { IOSLocationProvider() } bind LocationProvider::class
    single { IosReverseGeocodingService() } bind ReverseGeocodingService::class
}