package nick.mirosh.newsapp.di

import androidx.room.Room
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.ios.PermissionsController as PermissionsControllerIOS
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import nick.mirosh.newsapp.data.database.AppDatabase
import nick.mirosh.newsappkmp.location.IOSLocationProvider
import nick.mirosh.newsappkmp.location.LocationProvider
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun createPlatformHttpClient(): HttpClient {
    return HttpClient(Darwin)
}

val iOSModule = module {
    factory { HttpClient(Darwin) }
    single<PermissionsController> { PermissionsControllerIOS()}

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
        val dbFilePath = documentDirectory() + "/my_room.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }

    single { IOSLocationProvider() } bind LocationProvider::class
}