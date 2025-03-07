package nick.mirosh.newsapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nick.mirosh.newsappkmp.data.model.DatabaseArticle

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles():  List<DatabaseArticle>

    @Query("SELECT * FROM articles WHERE liked = 1")
    fun getLikedArticles(): Flow<List<DatabaseArticle>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<DatabaseArticle>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: DatabaseArticle): Long

    @Query("DELETE FROM articles")
    suspend fun deleteAll()
}