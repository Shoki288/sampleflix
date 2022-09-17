package com.example.search_repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core_cache.cache_home.dao.HomeRecommendDao
import com.example.core_retrofit.SearchBooksService
import com.example.core_unit_test.*
import com.example.core_unit_test.fixture.*
import com.example.extension.api.Success
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import retrofit2.Response

@ExperimentalCoroutinesApi

class SearchBookRepositoryTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @MockK
    private lateinit var service: SearchBooksService
    @MockK
    private lateinit var dao: HomeRecommendDao
    private lateinit var repository: SearchBookRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = SearchBookRepository(
            service = service,
            dao = dao,
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `searchBooksInit_キャッシュが存在しない場合、検索APIをリクエストすること `() {
        coEvery { dao.getAll() } returns emptyList()
        coEvery { repository.searchBookAndroid() } returns Response.error(
            400, "<mock>".toResponseBody("application/json; charset=utf-8".toMediaType())
        )
        coEvery { repository.searchBookFF() } returns Response.error(
            400, "<mock>".toResponseBody("application/json; charset=utf-8".toMediaType())
        )

        runBlocking { repository.searchBooksInit() }

        coVerify(exactly = 1) { repository.searchBookFF() }
        coVerify(exactly = 1) { repository.searchBookAndroid() }
    }

    @Test
    fun `searchBooksInit_検索APIが２つとも成功した場合レスポンスをマージしたものを返すこと`() {
        coEvery { dao.getAll() } returns emptyList()
        coEvery { repository.searchBookAndroid() } returns Response.success(
            createBookInfoList(list = (0..10).map { createBookInfo(id = "test$it") })
        )
        coEvery { repository.searchBookFF() } returns Response.success(
            createBookInfoList(list = (11..20).map { createBookInfo(id = "test$it") })
        )
        coEvery { repository.saveCache(createBookInfoList()) } just Runs
        coEvery { dao.insertAll(any()) } just Runs

        val response = runBlocking { repository.searchBooksInit() } as Success

        // Success同士で比較するとインスタンスの比較がされるためオブジェクトの比較を行っている
        Assert.assertEquals(
            createBookInfoList(list = (0..20).map {
                createBookInfo(
                    id = "test$it",
                    bookInfo = createVolumeInfo(authors = listOf("不明"))
                )
            }),
            response.data
        )
    }

    @Test
    fun `searchBooksInit_キャッシュが存在する場合、検索APIをリクエストしないこと`() {
        coEvery { dao.getAll() } returns listOf(createCacheBookInfo())

        runBlocking { repository.searchBooksInit() }

        coVerify(exactly = 0) { repository.searchBookFF() }
        coVerify(exactly = 0) { repository.searchBookAndroid() }
    }

    @Test
    fun `searchBooksInit_キャッシュが存在する場合、キャッシュを返すこと`() {
        coEvery { dao.getAll() } returns listOf(createCacheBookInfo())

        val response = runBlocking { repository.searchBooksInit() } as Success

        Assert.assertEquals(
            createBookInfoList(listOf(
                createBookInfo(
            bookInfo = createVolumeInfo(
                authors = listOf("authors"),
                categories = listOf("categories"),
                images = createImageLinks("image")
            )
            , accessInfo = null)
            )),
            response.data)
    }
}