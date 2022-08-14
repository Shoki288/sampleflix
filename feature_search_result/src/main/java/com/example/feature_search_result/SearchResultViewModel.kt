package com.example.feature_search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.example.entity.BookInfo
import com.example.extension.api.api
import com.example.extension.api.onException
import com.example.extension.api.onHttpError
import com.example.extension.api.onSuccess
import com.example.search_repository.usecase.SearchBookUseCase
import com.example.search_repository.usecase.SearchPagingBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    useCase: SearchPagingBooksUseCase,
    state: SavedStateHandle
): ViewModel() {

    val searchResult = useCase.searchBooks(
        requireNotNull(state.get<String>("keyword"))
    ).cachedIn(viewModelScope)

    private val _searchResultState = MutableStateFlow(SearchResultUiType.INIT_LOADING)
    val searchResultState = _searchResultState.asStateFlow()

    enum class SearchResultUiType {
        INIT_LOADING, LOADING, SUCCESS, ERROR
    }


    fun updateState(state: CombinedLoadStates) {
        _searchResultState.value = when(state.append) {
            is LoadState.Loading -> SearchResultUiType.LOADING
            is LoadState.Error -> SearchResultUiType.ERROR  // TODO エラーの判別したい
            is LoadState.NotLoading -> SearchResultUiType.SUCCESS
        }
    }
}