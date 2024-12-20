import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nick.mirosh.newsapp.domain.Result
import nick.mirosh.newsappkmp.domain.feed.repository.NewsRepository
import nick.mirosh.newsappkmp.ui.favorite.FavoriteArticlesScreenModel
import org.kodein.mock.Mock
import org.kodein.mock.generated.injectMocks
import org.kodein.mock.tests.TestsWithMocks
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class FavoriteArticlesScreenModelTest : TestsWithMocks() {

    override fun setUpMocks() = mocker.injectMocks(this)

    @Mock
    lateinit var repository: NewsRepository

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
//    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
//    private lateinit var mainDispatcher: TestDispatcher

    private val model by withMocks { FavoriteArticlesScreenModel(repository) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        println("before test")
//        mainDispatcher = UnconfinedTestDispatcher()
//        Dispatchers.setMain(mainThreadSurrogate)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain() // Reset to avoid side effects
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test() = runTest(
        UnconfinedTestDispatcher()
    ) {
        everySuspending { repository.getFavoriteArticles() } returns flowOf(
            Result.Success(
                emptyList()
            )
        )
        launch(Dispatchers.Main) {
            model.uiState.collect {
                println(it)
            }
        }
        advanceUntilIdle()
    }
}