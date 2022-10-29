package com.example.feature_home.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feature_home.HomeViewModel
import com.example.feature_home.R
import com.example.feature_home.compose.widget.RecommendBooks
import com.example.feature_home.compose.widget.RecommendCategories

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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
            books = viewModel.recentlyReadingBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // すぐ読める本
        RecommendBooks(
            title = stringResource(id = R.string.recommend_carousel_title),
            books = viewModel.recommendBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // プライム会員特定で読めるベストセラー
        RecommendBooks(
            title = stringResource(id = R.string.best_seller_carousel_title),
            books = viewModel.bestSellerBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 最近読んだ本に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_read_history_carousel_title),
            books = viewModel.recentlyReadHistoryBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // もうすぐ読み放題が終了するタイトル
        RecommendBooks(
            title = stringResource(id = R.string.end_unlimited_reading_carousel_title),
            books = viewModel.endUnlimitedReadingBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 近日配信開始のタイトルのおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.recently_release_carousel_title),
            books = viewModel.recentlyReleaseBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 類似タイトルに基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.similar_title_carousel_title),
            books = viewModel.similarTitleBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 読書履歴に基づくおすすめ
        RecommendBooks(
            title = stringResource(id = R.string.reading_history_carousel_title),
            books = viewModel.readingHistoryBooks.collectAsState().value,
            onClickItem = {
                Toast.makeText(context, "onClickItem", Toast.LENGTH_SHORT).show()
            },
            onClickShowAll = {
                Toast.makeText(context, "onClickShowAll", Toast.LENGTH_SHORT).show()
            }
        )

        // 本をさらに見る
        RecommendCategories(
            title = stringResource(id = R.string.recommend_category_carousel_title),
            categoryNameList = viewModel.categories.collectAsState().value,
            onClickItem = {

            }
        )
    }
}