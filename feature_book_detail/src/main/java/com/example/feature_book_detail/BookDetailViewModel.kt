package com.example.feature_book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.entity.BookInfo
import com.example.feature_book_detail.android_view.adapter.convertBookInfoToBookDetailInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    state: SavedStateHandle
): ViewModel() {

    private val _bookInfoDetail = MutableStateFlow(BookDetailInfoUiState(loading = true))
    val bookInfoDetail = _bookInfoDetail.asStateFlow()

    init {
        val args = requireNotNull(state.get<BookInfo>("book_info"))
        _bookInfoDetail.value = BookDetailInfoUiState(bookDetailInfo = convertBookInfoToBookDetailInfo(args))
    }
}