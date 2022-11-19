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
        data class OpenDescriptionDialog(val text: String): ViewEvent()
        data class OpenAboutDialog(val bookDetailInfo: BookDetailInfo): ViewEvent()
    }

    private val _bookInfoDetail = MutableStateFlow(BookDetailInfoUiState(loading = true))
    val bookInfoDetail = _bookInfoDetail.asStateFlow()
    
    private val _viewEvent = MutableSharedFlow<ViewEvent>()
    val viewEvent = _viewEvent.asSharedFlow()

    init {
        val args = requireNotNull(state.get<BookInfo>("book_info"))
        _bookInfoDetail.value = BookDetailInfoUiState(bookDetailInfo = convertBookInfoToBookDetailInfo(args))
    }
    
    fun onClickDescription() {
        viewModelScope.launch {
            _viewEvent.emit(ViewEvent.OpenDescriptionDialog(bookInfoDetail.value.bookDetailInfo.description))
        }
    }

    fun onClickAbout() {
        val aboutInfo = bookInfoDetail.value.bookDetailInfo
        viewModelScope.launch {
            _viewEvent.emit(ViewEvent.OpenAboutDialog(aboutInfo))
        }
    }
}