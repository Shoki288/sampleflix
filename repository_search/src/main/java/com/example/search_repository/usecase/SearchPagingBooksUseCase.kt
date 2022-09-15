package com.example.search_repository.usecase

import androidx.paging.PagingData
import com.example.entity.BookInfo
import com.example.search_repository.SearchBookRepository
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPagingBooksUseCase @Inject constructor(
    private val repository: SearchBookRepository
) {

    fun searchBooks(keyword: String, stateListener: (SearchResultState) -> Unit): Flow<PagingData<BookInfo>> {
        return repository.searchPagingBooks(keyword, stateListener)
    }

}