package com.example.sampleflix.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extension.api.Exception
import com.example.extension.api.HttpError
import com.example.extension.api.ApiZipSuccess
import com.example.search_repository.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SearchBookUseCase
): ViewModel() {

    private val _books = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val books = _books.asStateFlow()

    init {
        viewModelScope.launch {
            when(val res = useCase.searchBookInit()) {
                is ApiZipSuccess -> {
                    _books.value = HomeUiState.Success(res.data.first.items + res.data.second.items)
                }
                is HttpError -> {
                    _books.value = HomeUiState.ApiError(res.message)
                }
                is Exception -> {
                    _books.value = HomeUiState.ApiError(res.e.message ?: "接続できませんでした。もう一度時間をおいて確認してください。")
                }
                else -> {
                    _books.value = HomeUiState.ApiError("接続できませんでした。もう一度時間をおいて確認してください。")
                }
            }
        }
    }

}