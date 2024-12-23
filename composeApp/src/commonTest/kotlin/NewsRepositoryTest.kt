import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nick.mirosh.newsapp.data.database.ArticleDao
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.data.model.DatabaseArticle
import nick.mirosh.newsappkmp.data.repository.NewsRemoteDataSource
import nick.mirosh.newsappkmp.data.repository.NewsRepositoryImpl
import org.kodein.mock.Mock
import org.kodein.mock.generated.injectMocks
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsRepositoryTest : TestsWithMocks() {

    override fun setUpMocks() = mocker.injectMocks(this)

    @Mock
    lateinit var remoteDataSource: NewsRemoteDataSource

    @Mock
    lateinit var localDataSource: ArticleDao

    private val repository by withMocks { NewsRepositoryImpl(remoteDataSource, localDataSource) }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun balls() = runTest(UnconfinedTestDispatcher()) {

        //Arrange
        val expectedResult = Result.Success(
            listOf(
                Article(
                    "myAuthor",
                    "balls",
                    "url",
                    "urlToImage",
                    "publishedAt",
                    false
                )
            )
        )
        every { localDataSource.getLikedArticles() } returns flow {
            emit(
                listOf(
                    DatabaseArticle(
                        "myAuthor",
                        "description",
                        "url",
                        "urlToImage",
                        "publishedAt",
                        false,
                    )

                )
            )
        }

        //Act
        var result: List<Result<List<Article>>> = emptyList()

        val job = launch {
            result = repository.getFavoriteArticles().toList()
        }
        job.cancel()

        //Assert
        assertEquals(1, result.size)
        assertEquals(expectedResult, result[0] )
    }

}