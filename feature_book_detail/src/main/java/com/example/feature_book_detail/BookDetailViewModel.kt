package com.example.feature_book_detail

import androidx.lifecycle.ViewModel
import com.example.entity.BookInfo
import com.example.feature_book_detail.android_view.adapter.convertBookInfoToBookDetailInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*

class BookDetailViewModel @AssistedInject constructor(
    @Assisted bookInfo: BookInfo
): ViewModel() {

    private val _bookInfoDetail = MutableStateFlow(BookDetailInfoUiState(loading = true))
    val bookInfoDetail = _bookInfoDetail.asStateFlow()

    init {
        _bookInfoDetail.value = BookDetailInfoUiState(
            bookDetailInfo = convertBookInfoToBookDetailInfo(bookInfo)
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(bookInfo: BookInfo): BookDetailViewModel
    }
}