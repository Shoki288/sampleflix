package com.example.feature_home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core_unit_test.MainCoroutineScopeRule
import com.example.core_unit_test.createBookInfo
import com.example.core_unit_test.createBookInfoList
import com.example.extension.api.Exception
import com.example.extension.api.HttpError
import com.example.extension.api.Success
import com.example.search_repository.usecase.SearchBookUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    @MockK(relaxed = true)
    private lateinit var useCase: SearchBookUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createViewModel(useCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel(useCase: SearchBookUseCase) {
        viewModel = HomeViewModel(searchUseCase = useCase)
    }

    @Test
    fun `init_レコメンド検索を呼んでない場合、ロード中のステートを投げること`() {
        createViewModel(
            useCase.apply {
                coJustRun { useCase.searchBookInit() }
            }
        )

        assertTrue(viewModel.books.value is HomeUiState.Loading)
    }

    @Test
    fun `init_レコメンド検索が成功した場合、レコメンド結果を表示させること`() {
        val response = createBookInfoList()

        createViewModel(
            useCase.apply {
                coEvery { useCase.searchBookInit() } returns Success(data = response)
            }
        )

        assertTrue(viewModel.books.value is HomeUiState.Success)
    }

    @Test
    fun `init_レコメンド検索がAPIエラーだった場合、APIエラーのステートを表示させること`() {
        createViewModel(
            useCase.apply {
                coEvery { useCase.searchBookInit() } returns HttpError(code = 404, "エラー")
            }
        )

        assertTrue(viewModel.books.value is HomeUiState.Error.ApiError)
    }

    @Test
    fun `init_レコメンド検索がAPIエラー以外のエラーだった場合、ネットワークエラーのステートを表示させること`() {
        createViewModel(
            useCase.apply {
                coEvery { useCase.searchBookInit() } returns Exception(Throwable())
            }
        )

        assertTrue(viewModel.books.value is HomeUiState.Error.NetworkError)
    }

//    @Test
//    fun `recentlyReadingBooks_レコメンド検索のレスポンスの0から10番目の値を表示させること`() {
//        val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (0..10).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.recentlyReadingBooks.value)
//    }
//
//    @Test
//    fun `recommendBooks_レコメンド検索のレスポンスの11から20番目を読み始めたシリーズを続けるとして表示させること`() {
//        val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (11..20).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.recommendBooks.value)
//    }
//
//    @Test
//    fun `bestSellerBooks_レコメンド検索のレスポンスの21から30番目をすぐ読める本として表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (21..30).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.bestSellerBooks.value)
//    }
//
//    @Test
//    fun `recentlyReadHistoryBooks_レコメンド検索のレスポンスの31から40番目をプライム会員特定で読めるベストセラーとして表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (31..40).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.recentlyReadHistoryBooks.value)
//    }
//
//    @Test
//    fun `endUnlimitedReadingBooks_レコメンド検索のレスポンスの41から50番目を最近読んだ本に基づくおすすめとして表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (41..50).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.endUnlimitedReadingBooks.value)
//    }
//
//    @Test
//    fun `recentlyReleaseBooks_レコメンド検索のレスポンスの51から60番目をもうすぐ読み放題が終了するタイトルとして表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (51..60).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.recentlyReleaseBooks.value)
//    }
//
//    @Test
//    fun `similarTitleBooks_レコメンド検索のレスポンスの61から70番目を近日配信開始のタイトルのおすすめとして表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (61..70).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.similarTitleBooks.value)
//    }
//
//    @Test
//    fun `readingHistoryBooks_レコメンド検索のレスポンスの71から80番目を類似タイトルに基づくおすすめとして表示させること`() {
//         val response = createBookInfoList(
//            list = (0..79).map { createBookInfo(id = it.toString()) }
//        )
//        val expectedResponse = (71..80).map { createBookInfo(id = it.toString()) }
//
//        createViewModel(
//            useCase.apply {
//                coEvery { useCase.searchBookInit() } returns Success(data = response)
//            }
//        )
//
//        assertEquals(expectedResponse, viewModel.readingHistoryBooks.value)
//    }
}