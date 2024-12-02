package nick.mirosh.newsappkmp.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import nick.mirosh.newsapp.data.database.AppDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

// shared/src/iosMain/kotlin/Database.kt

//fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
//    val dbFilePath = documentDirectory() + "/my_room.db"
//    return Room.databaseBuilder<AppDatabase>(
//        name = dbFilePath,
//    )
//}
//
//@OptIn(ExperimentalForeignApi::class)
//private fun documentDirectory(): String {
//  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//    directory = NSDocumentDirectory,
//    inDomain = NSUserDomainMask,
//    appropriateForURL = null,
//    create = false,
//    error = null,
//  )
//  return requireNotNull(documentDirectory?.path)
//}