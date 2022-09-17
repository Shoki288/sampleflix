package com.example.feature_home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entity.BookInfo
import com.example.extension.api.HttpError
import com.example.extension.api.Success
import com.example.feature_home.vo.HomeUiState
import com.example.repository_favorite.use_case.AddFavoriteListUseCase
import com.example.search_repository.usecase.GetRecommendBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecommendBookUseCase: GetRecommendBookUseCase,
    private val addFavoriteListUseCase: AddFavoriteListUseCase
) : ViewModel() {

    @VisibleForTesting
    val books = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    init {
        viewModelScope.launch {
            books.value = when (val res = getRecommendBookUseCase.searchBookInit()) {
                is Success -> HomeUiState.Success(res.data.items)
                is HttpError -> HomeUiState.Error.ApiError(res.message)
                else -> HomeUiState.Error.NetworkError
            }
        }

        viewModelScope.launch { books.collect() }
    }

    val isDisableUpdateFavoriteState = MutableStateFlow(false)

    // 読み始めたシリーズを続ける
    val recentlyReadingBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 0..10 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // すぐ読める本
    val recommendBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 11..20 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // プライム会員特定で読めるベストセラー
    val bestSellerBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 21..30 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 最近読んだ本に基づくおすすめ
    val recentlyReadHistoryBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 31..40 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // もうすぐ読み放題が終了するタイトル
    val endUnlimitedReadingBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 41..50 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 近日配信開始のタイトルのおすすめ
    val recentlyReleaseBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 51..60 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 類似タイトルに基づくおすすめ
    val similarTitleBooks = books.filterIsInstance<HomeUiState.Success>().map {
        it.books.filterIndexed { index, _ -> index in 61..70 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 読書履歴に基づくおすすめ
    val readingHistoryBooks: StateFlow<List<BookInfo>> =
        books.filterIsInstance<HomeUiState.Success>().map {
            it.books.filterIndexed { index, _ -> index in 71..79 }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // 本をさらに見る
    val categories = books.filterIsInstance<HomeUiState.Success>().map {
        // APIから取得したデータのカテゴリを吸い取っている
        val categories = it.books.map { books -> books.bookInfo.categories }.flatten().run {
            filterNot { list -> list.isEmpty() }.distinct()
        }
        categories.filterIndexed { index, _ -> index in 0..9 }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun updateFavoriteState(isCheck: Boolean, bookInfo: BookInfo) {
        viewModelScope.launch {
            isDisableUpdateFavoriteState.value = true
            if (isCheck) {
                addFavoriteListUseCase.addFavoriteList(bookInfo)
            }
            isDisableUpdateFavoriteState.value = false
        }
    }
}