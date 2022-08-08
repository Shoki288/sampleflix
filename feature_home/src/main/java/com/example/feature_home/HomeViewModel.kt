package com.example.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.extension.api.ApiException
import com.example.extension.api.HttpError
import com.example.extension.api.ApiSuccess
import com.example.feature_home.HomeUiState.ApiError
import com.example.entity.BookInfo
import com.example.search_repository.usecase.SearchBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: SearchBookUseCase
) : ViewModel() {

    private val books = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    init {
        viewModelScope.launch {
            books.value = when (val res = useCase.searchBookInit()) {
                is ApiSuccess -> HomeUiState.Success(res.data.items)
                is HttpError -> ApiError(res.message)
                is ApiException -> ApiError(res.e.message ?: "接続できませんでした。もう一度時間をおいて確認してください。")
                else -> ApiError("接続できませんでした。もう一度時間をおいて確認してください。")
            }
        }

        viewModelScope.launch { books.collect() }
    }


    // 読み始めたシリーズを続ける
    val recentlyReadingBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(0, 10)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // すぐ読める本
    val recommendBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(11, 20)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // プライム会員特定で読めるベストセラー
    val bestSellerBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(21, 30)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 最近読んだ本に基づくおすすめ
    val recentlyReadHistoryBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(31, 40)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // もうすぐ読み放題が終了するタイトル
    val endUnlimitedReadingBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(41, 50)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 近日配信開始のタイトルのおすすめ
    val recentlyReleaseBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(51, 60)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 類似タイトルに基づくおすすめ
    val similarTitleBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.subList(61, 70)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 読書履歴に基づくおすすめ
    val readingHistoryBooks: StateFlow<List<BookInfo>> =
        books.filterIsInstance<HomeUiState.Success>().map {
            it.books.subList(71, 79)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 本をさらに見る
    val categories = books.filterIsInstance<HomeUiState.Success>().map {
        // APIから取得したデータのカテゴリを吸い取っている
        val categories = it.books.map { books -> books.bookInfo.categories }.flatten().apply {
            filterNot { list -> list.isEmpty() }.distinct()
        }
        categories.subList(0, 9)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}