package com.example.feature_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repository_category.usecase.GetAllCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GetAllCategoryUseCase
): ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            val categories = useCase.getCategory()
            _categories.value = categories
        }
    }
}