import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsapp.domain.feed.model.Article
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesScreenModel
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesUIState
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@UsesMocks(NewsRepository::class)
class FavoriteArticlesScreenModelTest {

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun test() = runTest {
        val mocker = Mocker()
        val repository = mocker.mock<NewsRepository>()
        val article =
            Article(
                "myAuthor",
                "balls",
                "url",
                "urlToImage",
                "publishedAt",
                false
            )

        val expectedResult =
            FavoriteArticlesUIState.FavoriteArticles(
                listOf(article)
            )

        mocker.every { repository.getFavoriteArticles() } returns flowOf (
                Result.Success(listOf(article))
        )

        val result = mutableListOf<FavoriteArticlesUIState>()
        val model = FavoriteArticlesScreenModel(repository)

        val job = launch {
            model.uiState.take(2).toList(result)
        }


        advanceUntilIdle()

        job.cancel()
        job.join()


        assertEquals(FavoriteArticlesUIState.Loading, result[0])
        assertEquals(expectedResult, result[1])
    }
}