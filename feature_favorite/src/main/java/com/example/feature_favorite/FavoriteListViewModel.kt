package com.example.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entity.BookInfo
import com.example.feature_favorite.android_view.FavoriteListUiState
import com.example.repository_favorite.use_case.AddFavoriteListUseCase
import com.example.repository_favorite.use_case.DeleteFavoriteListUseCase
import com.example.repository_favorite.use_case.GetFavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val addFavoriteListUseCase: AddFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase
): ViewModel() {

    private val _favoriteList = MutableStateFlow<FavoriteListUiState>(FavoriteListUiState.Loading)
    val favoriteList = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch {
            _favoriteList.value = try {
                val response = getFavoriteListUseCase.fetchFavoriteList()
                if (response.isNotEmpty()) {
                    FavoriteListUiState.Success(response)
                } else {
                    FavoriteListUiState.Error.EmptyList
                }
            } catch (e: Exception) {
                FavoriteListUiState.Error.Exception
            }

        }
    }

    fun updateFavoriteState(bookInfo: BookInfo, isCheck: Boolean) {
        viewModelScope.launch {
            if (isCheck) {
                addFavoriteListUseCase.addFavoriteList(bookInfo)
            } else {
                deleteFavoriteListUseCase.deleteFavoriteItem(bookInfo)
            }
        }

        _favoriteList.update {
            if (it is FavoriteListUiState.Success) {
                it.copy(bookInfoList = it.bookInfoList.map { item ->
                    if (item.id == bookInfo.id) {
                        item.copy(isFavorite = isCheck)
                    } else {
                        item
                    }
                })
            } else {
                it
            }
        }
    }
}