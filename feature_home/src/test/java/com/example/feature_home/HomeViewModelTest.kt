package com.example.feature_home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core_unit_test.*
import com.example.core_unit_test.fixture.createBookInfo
import com.example.core_unit_test.fixture.createBookInfoList
import com.example.core_unit_test.fixture.createVolumeInfo
import com.example.extension.api.ApiResult
import com.example.extension.api.Exception
import com.example.extension.api.HttpError
import com.example.extension.api.Success
import com.example.feature_home.vo.HomeUiState
import com.example.repository_favorite.use_case.AddFavoriteListUseCase
import com.example.repository_favorite.use_case.DeleteFavoriteListUseCase
import com.example.search_repository.usecase.GetRecommendBookUseCase
import com.example.search_repository.usecase.UpdateRecommendFavoriteStateUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private lateinit var viewModel: HomeViewModel

    @MockK
    private lateinit var getRecommendBookUseCase: GetRecommendBookUseCase
    @MockK
    private lateinit var addFavoriteListUseCase: AddFavoriteListUseCase
    @MockK
    private lateinit var updateRecommendFavoriteStateUseCase: UpdateRecommendFavoriteStateUseCase
    @MockK
    private lateinit var deleteFavoriteListUseCase: DeleteFavoriteListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel() {
        viewModel = HomeViewModel(
            getRecommendBookUseCase = getRecommendBookUseCase,
            addFavoriteListUseCase = addFavoriteListUseCase,
            updateRecommendFavoriteStateUseCase = updateRecommendFavoriteStateUseCase,
            deleteFavoriteListUseCase = deleteFavoriteListUseCase,
        )
    }

    @Test
    fun `init_レコメンド検索が成功した場合、レコメンド結果を表示させること`() {
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(createBookInfoList())
        createViewModel()
        val observer = viewModel.books.asTestObserver()

        verify {
            observer.onChanged(HomeUiState.Success(createBookInfoList().items))
        }
    }

    @Test
    fun `init_レコメンド検索がAPIエラーだった場合、APIエラーのステートを表示させること`() {
        coEvery { getRecommendBookUseCase.searchBookInit() } returns HttpError(
            code = 400, message = "message"
        )
        createViewModel()
        val observer = viewModel.books.asTestObserver()

        verify {
            observer.onChanged(HomeUiState.Error.ApiError("message"))
        }
    }

    @Test
    fun `init_レコメンド検索がAPIエラー以外のエラーだった場合、ネットワークエラーのステートを表示させること`() {
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Exception(Throwable())
        createViewModel()
        val observer = viewModel.books.asTestObserver()

        verify {
            observer.onChanged(HomeUiState.Error.NetworkError)
        }
    }

    @Test
    fun `recentlyReadingBooks_レコメンド検索のレスポンスの0から10番目の値を表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (0..10).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.recentlyReadingBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }

    }

    @Test
    fun `recommendBooks_レコメンド検索のレスポンスの11から20番目を読み始めたシリーズを続けるとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (11..20).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.recommendBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `bestSellerBooks_レコメンド検索のレスポンスの21から30番目をすぐ読める本として表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (21..30).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.bestSellerBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `recentlyReadHistoryBooks_レコメンド検索のレスポンスの31から40番目をプライム会員特定で読めるベストセラーとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (31..40).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.recentlyReadHistoryBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `endUnlimitedReadingBooks_レコメンド検索のレスポンスの41から50番目を最近読んだ本に基づくおすすめとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (41..50).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.endUnlimitedReadingBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `recentlyReleaseBooks_レコメンド検索のレスポンスの51から60番目をもうすぐ読み放題が終了するタイトルとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (51..60).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.recentlyReleaseBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `similarTitleBooks_レコメンド検索のレスポンスの61から70番目を近日配信開始のタイトルのおすすめとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (61..70).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.similarTitleBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `readingHistoryBooks_レコメンド検索のレスポンスの71から79番目を類似タイトルに基づくおすすめとして表示させること`() {
        val response = createBookInfoList(list = (0..79).map { createBookInfo(id = it.toString()) })
        val expectedResponse = (71..79).map { createBookInfo(id = it.toString()) }
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.readingHistoryBooks.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `categories_レコメンド検索のレスポンスのカテゴリ情報から重複なしで吸い出したものを表示させること`() {
        val response = createBookInfoList(list = (0..79).map {
            createBookInfo(
                id = it.toString(),
                bookInfo = createVolumeInfo(categories = listOf("${it%10}"))
            )
        })
        val expectedResponse = List(10){it.toString()}
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.categories.asTestObserver()

        verify { observer.onChanged(expectedResponse) }
    }

    @Test
    fun `updateFavoriteState_お気に入り登録された場合、ローカルDBで持つお気に入り商品が更新されること`() {
        coEvery { addFavoriteListUseCase.addFavoriteList(any()) } just Runs
        coEvery { updateRecommendFavoriteStateUseCase.updateFavoriteState(any()) } just Runs
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(createBookInfoList())
        val bookInfo = createBookInfo()
        createViewModel()

        viewModel.updateFavoriteState(isCheck = true, bookInfo = bookInfo)

        coVerify {
            updateRecommendFavoriteStateUseCase.updateFavoriteState(
                bookInfo.copy(volumeInfo = bookInfo.volumeInfo.copy(isFavorite = true))
            )
        }
    }

    @Test
    fun `updateFavoriteState_お気に入り登録された場合、レコメンドが更新されること`() {
        val response = createBookInfoList(list = (0..10).map {
            createBookInfo(
                id = it.toString(),
                bookInfo = createVolumeInfo(categories = listOf("${it%10}"))
            )
        })
        coEvery { addFavoriteListUseCase.addFavoriteList(any()) } just Runs
        coEvery { updateRecommendFavoriteStateUseCase.updateFavoriteState(any()) } just Runs
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.books.asTestObserver()

        viewModel.updateFavoriteState(isCheck = true, createBookInfo(id = "3", bookInfo = createVolumeInfo(categories = listOf("3"))))

        verify {
            observer.onChanged(
                HomeUiState.Success(
                    response.items.mapIndexed { index, bookInfo ->
                        if (index == 3) {
                            bookInfo.copy(volumeInfo = bookInfo.volumeInfo.copy(isFavorite = true))
                        } else {
                            bookInfo
                        }
                    }
                )
            )
        }
    }

    @Test
    fun `updateFavoriteState_お気に入り登録が解除された場合、レコメンドが更新されること`() {
        val response = createBookInfoList(list = (0..10).map {
            createBookInfo(
                id = it.toString(),
                bookInfo = createVolumeInfo(
                    categories = listOf("${it%10}"),
                    isFavorite = true
                )
            )
        })
        coEvery { addFavoriteListUseCase.addFavoriteList(any()) } just Runs
        coEvery { updateRecommendFavoriteStateUseCase.updateFavoriteState(any()) } just Runs
        coEvery { getRecommendBookUseCase.searchBookInit() } returns Success(response)
        createViewModel()
        val observer = viewModel.books.asTestObserver()

        viewModel.updateFavoriteState(isCheck = false, createBookInfo(id = "3", bookInfo = createVolumeInfo(categories = listOf("3"))))

        verify {
            observer.onChanged(
                HomeUiState.Success(
                    response.items.mapIndexed { index, bookInfo ->
                        if (index == 3) {
                            bookInfo.copy(volumeInfo = bookInfo.volumeInfo.copy(isFavorite = false))
                        } else {
                            bookInfo
                        }
                    }
                )
            )
        }
    }
}