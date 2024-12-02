package nick.mirosh.newsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.mirosh.newsapp.data.models.DatabaseArticle

const val DATABASE_NAME = "articles-db"

@Database(entities = [DatabaseArticle::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}