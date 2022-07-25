package com.example.search_repository

import com.example.core_cache.dao.BookInfoDao
import com.example.core_retrofit.SearchBooksService
import com.example.extension.api.*
import com.example.extension.dispatcher.DefaultDispatcher
import com.example.model.BookInfoList
import com.example.model.adeapter.bookInfoListAdapter
import com.example.model.adeapter.cacheBookInfoAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class SearchBookRepository @Inject constructor(
    private val service: SearchBooksService,
    private val dao: BookInfoDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun searchBooksInit(): Result<BookInfoList> =
        withContext(defaultDispatcher) {
            val cache = dao.getAll()
            if (cache.isEmpty()) {
                zip(
                    firstExecute = { async { searchBookAndroid() } }, secondExecute = { async { searchBookFF() } },
                ).onZipSuccess { first, second ->
                    val response = BookInfoList(first.items + second.items)
                    Success(data = response)
                    saveCache(response)
                }.onHttpError { code, message ->
                    HttpError<BookInfoList>(code, message)
                }.onException { e ->
                    Exception<BookInfoList>(e)
                }
            } else {
                Success(data = cacheBookInfoAdapter(cache))
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
}