package com.example.search_repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core_cache.cache_home.dao.HomeRecommendDao
import com.example.core_retrofit.SearchBooksService
import com.example.entity.BookInfo
import com.example.extension.api.*
import com.example.extension.dispatcher.DefaultDispatcher
import com.example.entity.BookInfoList
import com.example.entity.adeapter.bookInfoListAdapter
import com.example.entity.adeapter.cacheBookInfoAdapter
import com.example.entity.adeapter.updateBookInfo
import com.example.search_repository.SearchBooksPagingSource.SearchBooksPagingSourceFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchBookRepository @Inject constructor(
    private val service: SearchBooksService,
    private val dao: HomeRecommendDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun searchBooksInit(): Result<BookInfoList> =
        withContext(defaultDispatcher) {
            val cache = dao.getAll()
            if (cache.isEmpty()) {
                zip(
                    firstExecute = { async { searchBookAndroid() } }, secondExecute = { async { searchBookFF() } },
                ).onZipSuccess { first, second ->
                    val response = updateBookInfo(first.items + second.items)
                    ApiSuccess(data = response)
                    saveCache(response)
                }.onHttpError { code, message ->
                    HttpError<BookInfoList>(code, message)
                }.onException { e ->
                    ApiException<BookInfoList>(e)
                }
            } else {
                ApiSuccess(data = cacheBookInfoAdapter(cache))
            }
        }
    private suspend fun searchBookAndroid(): Response<BookInfoList> =
        searchBooks(keyword = "android", maxResultSize = 40)
    private suspend fun searchBookFF(): Response<BookInfoList> =
        searchBooks(keyword = "FF14", maxResultSize = 40)

    suspend fun searchBooks(keyword: String, maxResultSize: Int? = null): Response<BookInfoList> =
        service.searchBooks(keyword, maxResultSize)

    private suspend fun saveCache(items: BookInfoList) {
        dao.insertAll(bookInfoListAdapter(items))
    }

    @Inject
    lateinit var factory: SearchBooksPagingSourceFactory
    fun searchPagingBooks(keyword: String): Flow<PagingData<BookInfo>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { factory.create(keyword) }
    ).flow
}