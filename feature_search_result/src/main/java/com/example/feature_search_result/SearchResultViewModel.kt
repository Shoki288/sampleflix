package com.example.feature_search_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.entity.BookInfo
import com.example.repository_favorite.use_case.AddFavoriteListUseCase
import com.example.repository_favorite.use_case.DeleteFavoriteListUseCase
import com.example.search_repository.SearchBooksPagingSource.SearchResultState
import com.example.search_repository.usecase.SearchPagingBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    useCase: SearchPagingBooksUseCase,
    private val addFavoriteListUseCase: AddFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase,
    state: SavedStateHandle,
): ViewModel() {

    val searchResult = useCase.searchBooks(
        keyword = requireNotNull(state.get<String>("keyword")),
        stateListener = { _searchResultState.value = it }
    ).cachedIn(viewModelScope)

    private val _searchResultState = MutableStateFlow(SearchResultState.LOADING)
    val searchResultState = _searchResultState.asStateFlow()

    fun updateFavoriteState(isFavorite: Boolean, bookInfo: BookInfo) {
        viewModelScope.launch {
            if (isFavorite) {
                addFavoriteListUseCase.addFavoriteList(bookInfo)
            } else {
                deleteFavoriteListUseCase.deleteFavoriteItem(bookInfo)
            }
            // TODO RemoteMediatorに変更しないと更新できないかも
        }
    }
}