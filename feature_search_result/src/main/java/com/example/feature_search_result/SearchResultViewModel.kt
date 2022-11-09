package com.example.feature_search_result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import com.example.search_repository.usecase.SearchPagingBooksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchResultViewModel @AssistedInject constructor(
    useCase: SearchPagingBooksUseCase,
    @Assisted keyword: String
): ViewModel() {

    val searchResult = useCase.searchBooks(
        keyword = keyword,
        stateListener = { _searchResultState.value = it }
    ).cachedIn(viewModelScope)

    private val _searchResultState = MutableStateFlow(SearchResultState.LOADING)
    val searchResultState = _searchResultState.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(keyword: String): SearchResultViewModel
    }
}