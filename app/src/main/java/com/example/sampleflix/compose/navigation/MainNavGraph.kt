package com.example.sampleflix.compose.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.entity.BookInfoListResponse
import com.example.extension.convertArgumentToJson
import com.example.extension.getNotNullParcelable
import com.example.feature_book_detail.compose.BookDetailRoute
import com.example.feature_favorite.compose.FavoriteListRoute
import com.example.feature_home.compose.HomeScreenRoute
import com.example.feature_recommend_list.compose.RecommendListScreen
import com.example.feature_search.compose.SearchTopScreen
import com.example.feature_search_result.compose.SearchResultRoute

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    // ホーム画面
    composable(BottomNavigationScreen.Home.route) {
        HomeScreenRoute(
            onCategoryClick = { keyword ->
                navController.openHasArgumentScreen(SearchResult.route, keyword)
            },
            onClickShowAll = { title, bookInfoList ->
                navController.openHasArgumentScreen(Recommend.route, title, convertArgumentToJson(bookInfoList))
            }
        )
    }
    // レコメンド画面
    composable(
        route = Recommend.routeWithArgs,
        arguments = Recommend.argument
    ) { navBackStackEntry ->
        val title = navBackStackEntry.arguments?.getString(Recommend.titleArgs) ?: ""
        val bookInfoList = navBackStackEntry.arguments?.getNotNullParcelable<BookInfoListResponse>(Recommend.bookInfoArgs) ?: BookInfoListResponse(emptyList())

        RecommendListScreen(
            title = title,
            bookInfoList = bookInfoList.items
        )
    }

    // 検索TOP
    composable(BottomNavigationScreen.SearchTop.route) {
        SearchTopScreen(
            onSearch = { keyword ->
                navController.openHasArgumentScreen(SearchResult.route, keyword)
            }
        )
    }

    // お気に入りリスト
    composable(BottomNavigationScreen.Favorite.route) {
        FavoriteListRoute(
            onClickItem = { bookInfo ->
                navController.openHasArgumentScreen(BookDetail.route, convertArgumentToJson(bookInfo))
            }
        )
    }

    // 検索結果
    composable(
        route = SearchResult.routeWithArgs,
        arguments = SearchResult.argument
    ) { navBackStackEntry ->
        val keyword = navBackStackEntry.arguments?.getString(SearchResult.keywordArgs)
        SearchResultRoute(
            arg = keyword,
            onClickItem = { bookInfo ->
                navController.openHasArgumentScreen(BookDetail.route, convertArgumentToJson(bookInfo))
            }
        )
    }

    // 商品詳細
    composable(
        route = BookDetail.routeWithArgs,
        arguments = BookDetail.argument
    ) {
        BookDetailRoute()
    }
}

fun NavHostController.openHasArgumentScreen(route: String, vararg arg: Any) {
    openScreen("$route/${arg.joinToString("/")}")
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavBackStackEntry.rememberParentEntry(navController: NavHostController): NavBackStackEntry {
    val parentId = destination.parent!!.id
    return remember { navController.getBackStackEntry(parentId) }
}

fun NavHostController.openScreen(root: String) {
    this.navigate(root) {
        popUpTo(this@openScreen.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}