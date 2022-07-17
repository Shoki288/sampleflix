package com.example.sampleflix.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.search_repository.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SearchBookUseCase
): ViewModel() {

    init {
        viewModelScope.launch {
            useCase.searchBookInit()
        }
    }

}