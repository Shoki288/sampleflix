package com.example.feature_book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entity.BookInfo
import com.example.feature_book_detail.android_view.adapter.convertBookInfoToBookDetailInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    state: SavedStateHandle
): ViewModel() {
    sealed class ViewEvent {
        object None: ViewEvent()
        data class OpenDetailInfoDialog(val text: String): ViewEvent()
    }

    private val _bookInfoDetail = MutableStateFlow(BookDetailInfoUiState(loading = true))
    val bookInfoDetail = _bookInfoDetail.asStateFlow()
    
    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    init {
        val args = requireNotNull(state.get<BookInfo>("book_info"))
        _bookInfoDetail.value = BookDetailInfoUiState(bookDetailInfo = convertBookInfoToBookDetailInfo(args))
    }
    
    fun onClickDescription(text: String) {
        viewModelScope.launch {
            _viewEvent.emit(ViewEvent.OpenDetailInfoDialog(text))
        }
    }
}