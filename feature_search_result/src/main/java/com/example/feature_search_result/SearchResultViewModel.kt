package com.example.feature_search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import com.example.search_repository.usecase.SearchPagingBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    useCase: SearchPagingBooksUseCase,
    state: SavedStateHandle
): ViewModel() {

    val searchResult = useCase.searchBooks(
        keyword = requireNotNull(state.get<String>("keyword")),
        stateListener = { _searchResultState.value = it }
    ).cachedIn(viewModelScope)

    private val _searchResultState = MutableStateFlow(SearchResultState.LOADING)
    val searchResultState = _searchResultState.asStateFlow()
}