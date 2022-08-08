package com.example.feature_search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entity.BookInfo
import com.example.extension.api.api
import com.example.extension.api.onException
import com.example.extension.api.onHttpError
import com.example.extension.api.onSuccess
import com.example.search_repository.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val useCase: SearchBookUseCase,
    state: SavedStateHandle
): ViewModel() {

    init {
        search(requireNotNull(state.get<String>("keyword")))
    }

    private val _searchResult = MutableStateFlow<SearchResultUiState>(SearchResultUiState.Loading)
    val searchResult = _searchResult.asStateFlow()

    fun search(keyword: String) {
        viewModelScope.launch {
            api {
                useCase.searchBook(keyword)
            }.onSuccess {
                _searchResult.value = SearchResultUiState.Success(it.items)
            }.onHttpError { _, message ->
                _searchResult.value = SearchResultUiState.Error(message)
            }.onException { e ->
                _searchResult.value = SearchResultUiState.Error(e.message ?: "エラー")
            }
        }
    }

    fun findBookInfoById(id: String): BookInfo? {
        searchResult.value.let {
            return if (it is SearchResultUiState.Success) {
                it.results.first { result -> result.id == id }
            } else null
        }
    }
}