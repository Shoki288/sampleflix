package com.example.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extension.api.AppException
import com.example.extension.api.HttpError
import com.example.extension.api.Success
import com.example.model.BookInfo
import com.example.search_repository.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SearchBookUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val books = _books.asStateFlow()

    init {
        viewModelScope.launch {
            when (val res = useCase.searchBookInit()) {
                is Success -> {
                    _books.value = HomeUiState.Success(res.data.items)
                }
                is HttpError -> {
                    _books.value = HomeUiState.ApiError(res.message)
                }
                is AppException -> {
                    _books.value =
                        HomeUiState.ApiError(res.e.message ?: "接続できませんでした。もう一度時間をおいて確認してください。")
                }
                else -> {
                    _books.value = HomeUiState.ApiError("接続できませんでした。もう一度時間をおいて確認してください。")
                }
            }
        }

        viewModelScope.launch { _books.collect() }
    }


    // 読み始めたシリーズを続ける
    val recentlyReadingBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(0, 10)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // すぐ読める本
    val recommendBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(11, 20)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // プライム会員特定で読めるベストセラー
    val bestSellerBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(21, 30)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 最近読んだ本に基づくおすすめ
    val recentlyReadHistoryBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(31, 40)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // もうすぐ読み放題が終了するタイトル
    val endUnlimitedReadingBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(41, 50)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 近日配信開始のタイトルのおすすめ
    val recentlyReleaseBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(51, 60)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 類似タイトルに基づくおすすめ
    val similarTitleBooks = _books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(61, 70)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 読書履歴に基づくおすすめ
    val readingHistoryBooks: StateFlow<List<BookInfo>> =
        _books.filterIsInstance<HomeUiState.Success>().map {
            it.books.subList(71, 79)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 本をさらに見る
    val categories = _books.filterIsInstance<HomeUiState.Success>().map {
        val categories = it.books.map { books -> books.bookInfo.categories }.flatten().filterNot { list -> list.isEmpty() }.distinct()
        categories.subList(0, 9)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}