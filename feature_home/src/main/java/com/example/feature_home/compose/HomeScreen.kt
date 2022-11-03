package com.example.feature_home.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.entity.BookInfo
import com.example.feature_home.HomeViewModel
import com.example.feature_home.R
import com.example.feature_home.compose.widget.RecommendBooks
import com.example.feature_home.compose.widget.RecommendCategories
import com.example.feature_home.compose.widget.SingleBookDetailBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onCategoryClick: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        modifier = Modifier.fillMaxSize(),
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
            recentlyReadingBooks = viewModel.recentlyReadingBooks.collectAsState().value,
            recommendBooks = viewModel.recommendBooks.collectAsState().value,
            bestSellerBooks = viewModel.bestSellerBooks.collectAsState().value,
            recentlyReadHistoryBooks = viewModel.recentlyReadHistoryBooks.collectAsState().value,
            endUnlimitedReadingBooks = viewModel.endUnlimitedReadingBooks.collectAsState().value,
            recentlyReleaseBooks = viewModel.recentlyReleaseBooks.collectAsState().value,
            similarTitleBooks = viewModel.similarTitleBooks.collectAsState().value,
            readingHistoryBooks = viewModel.readingHistoryBooks.collectAsState().value,
            categories = viewModel.categories.collectAsState().value
        )
    }
}

@Composable
fun HomeScreen(
    onItemClick: (BookInfo) -> Unit,
    onCategoryClick: (String) -> Unit,
    recentlyReadingBooks: List<BookInfo>,
    recommendBooks: List<BookInfo>,
    bestSellerBooks: List<BookInfo>,
    recentlyReadHistoryBooks: List<BookInfo>,
    endUnlimitedReadingBooks: List<BookInfo>,
    recentlyReleaseBooks: List<BookInfo>,
    similarTitleBooks: List<BookInfo>,
    readingHistoryBooks: List<BookInfo>,
    categories: List<String>
) {
    val context = LocalContext.current

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
            books = recentlyReadingBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // すぐ読める本
        RecommendBooks(
            title = stringResource(id = R.string.recommend_carousel_title),
            books = recommendBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // プライム会員特定で読めるベストセラー
        RecommendBooks(
            title = stringResource(id = R.string.best_seller_carousel_title),
            books = bestSellerBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 最近読んだ本に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_read_history_carousel_title),
            books = recentlyReadHistoryBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // もうすぐ読み放題が終了するタイトル
        RecommendBooks(
            title = stringResource(id = R.string.end_unlimited_reading_carousel_title),
            books = endUnlimitedReadingBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 近日配信開始のタイトルのおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_release_carousel_title),
            books = recentlyReleaseBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 類似タイトルに基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.similar_title_carousel_title),
            books = similarTitleBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 読書履歴に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.reading_history_carousel_title),
            books = readingHistoryBooks,
            onClickItem = onItemClick,
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 本をさらに見る
        RecommendCategories(
            title = stringResource(id = R.string.recommend_category_carousel_title),
            categoryNameList = categories,
            onClickItem = onCategoryClick,
        )
    }
}