package com.example.feature_home.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core_design.theme.AppTheme
import com.example.entity.BookInfo
import com.example.entity.BookInfoListResponse
import com.example.feature_home.HomeViewModel
import com.example.feature_home.R
import com.example.feature_home.compose.widget.RecommendBooks
import com.example.feature_home.compose.widget.RecommendCategories
import com.example.feature_home.compose.widget.SingleBookDetailBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRoot(
    viewModel: HomeViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit,
    onClickShowAll: (String, BookInfoListResponse) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    AppTheme {
        Scaffold { paddingValue ->
            ModalBottomSheetLayout(
                sheetState = sheetState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                sheetContent = {
                    SingleBookDetailBottomSheet(
                        uiState = viewModel.selectBook.collectAsState().value,
                        onClickClose = { scope.launch { sheetState.hide() } },
                    )
                }
            ) {
                HomeScreen(
                    onItemClick = {
                        viewModel.onItemClick(it)
                        scope.launch { sheetState.show() }
                    },
                    onCategoryClick = onCategoryClick,
                    onClickShowAll = onClickShowAll,
                    onClickFavorite = { isFavorite, bookInfo -> viewModel.updateFavoriteState(isFavorite, bookInfo) },
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    onItemClick: (BookInfo) -> Unit,
    onClickShowAll: (String, BookInfoListResponse) -> Unit,
    onCategoryClick: (String) -> Unit,
    onClickFavorite: (Boolean, BookInfo) -> Unit,
    viewModel: HomeViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // 読み始めたシリーズを続ける
        RecommendBooks(
            title = stringResource(id = R.string.recently_reading_carousel_title),
            books = viewModel.recentlyReadingBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // すぐ読める本
        RecommendBooks(
            title = stringResource(id = R.string.recommend_carousel_title),
            books = viewModel.recommendBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // プライム会員特定で読めるベストセラー
        RecommendBooks(
            title = stringResource(id = R.string.best_seller_carousel_title),
            books = viewModel.bestSellerBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // 最近読んだ本に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_read_history_carousel_title),
            books = viewModel.recentlyReadHistoryBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // もうすぐ読み放題が終了するタイトル
        RecommendBooks(
            title = stringResource(id = R.string.end_unlimited_reading_carousel_title),
            books = viewModel.endUnlimitedReadingBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // 近日配信開始のタイトルのおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_release_carousel_title),
            books = viewModel.recentlyReleaseBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // 類似タイトルに基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.similar_title_carousel_title),
            books = viewModel.similarTitleBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // 読書履歴に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.reading_history_carousel_title),
            books = viewModel.readingHistoryBooks.collectAsState(),
            onClickItem = onItemClick,
            onClickShowAll = onClickShowAll,
            onClickFavorite = onClickFavorite
        )

        // 本をさらに見る
        RecommendCategories(
            title = stringResource(id = R.string.recommend_category_carousel_title),
            categoryNameList = viewModel.categories.collectAsState().value,
            onClickItem = onCategoryClick,
        )
    }
}