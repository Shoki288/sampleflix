package com.example.feature_favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entity.CacheBookInfo
import com.example.repository_favorite.use_case.FavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val useCase: FavoriteListUseCase
): ViewModel() {

    init {
        viewModelScope.launch {
            _favoriteList.value = useCase.fetchFavoriteList()
        }
    }

    private val _favoriteList = MutableStateFlow<List<CacheBookInfo>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()
}