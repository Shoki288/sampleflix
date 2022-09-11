package com.example.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository_favorite.use_case.GetFavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val useCase: GetFavoriteListUseCase
): ViewModel() {

    private val _favoriteList = MutableStateFlow<FavoriteListUiState>(FavoriteListUiState.Loading)
    val favoriteList = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch {
            _favoriteList.value = try {
                val response = useCase.fetchFavoriteList()
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
}