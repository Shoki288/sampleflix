package com.example.search_repository

import androidx.annotation.VisibleForTesting
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core_cache.cache_home.dao.HomeRecommendDao
import com.example.core_retrofit.SearchBooksService
import com.example.entity.BookInfo
import com.example.entity.BookInfoList
import com.example.entity.adeapter.bookInfoListAdapter
import com.example.entity.adeapter.cacheBookInfoAdapter
import com.example.entity.adeapter.updateBookInfo
import com.example.extension.api.*
import com.example.search_repository.SearchBooksPagingSource.SearchBooksPagingSourceFactory
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchBookRepository @Inject constructor(
    private val service: SearchBooksService,
    private val dao: HomeRecommendDao,
) {

    suspend fun searchBooksInit(): ApiResult<BookInfoList> =
        withContext(Dispatchers.IO) {
            val cache = dao.getAll()
            if (cache.isEmpty()) {
                zip(
                    firstExecute = { async { searchBookAndroid() } },
                    secondExecute = { async { searchBookFF() } },
                    { first, second -> updateBookInfo(first.items + second.items) }
                ).onSuccess { saveCache(it) }
            } else {
                Success(data = cacheBookInfoAdapter(cache))
            }
        }

    @VisibleForTesting
    suspend fun searchBookAndroid(): Response<BookInfoList> = searchBooks(keyword = "android", maxResultSize = 40)
    @VisibleForTesting
    suspend fun searchBookFF(): Response<BookInfoList> = searchBooks(keyword = "FF14", maxResultSize = 40)

    suspend fun searchBooks(keyword: String, maxResultSize: Int? = null): Response<BookInfoList> =
        service.searchBooks(keyword, maxResultSize)

    @VisibleForTesting
    suspend fun saveCache(items: BookInfoList) {
        dao.insertAll(bookInfoListAdapter(items))
    }

    @Inject
    lateinit var factory: SearchBooksPagingSourceFactory
    fun searchPagingBooks(keyword: String, errorListener: (SearchResultState) -> Unit): Flow<PagingData<BookInfo>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { factory.create(keyword, errorListener) }
    ).flow
}