package com.example.sampleflix.compose.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature_favorite.compose.FavoriteListScreen
import com.example.feature_home.compose.HomeScreenRoute
import com.example.feature_search.compose.SearchTopScreen
import com.example.feature_search_result.compose.SearchResultRoute

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    // ホーム画面
    composable(BottomNavigationScreen.Home.route) {
        HomeScreenRoute(
            onCategoryClick = {}
        )
    }

    // 検索TOP
    composable(BottomNavigationScreen.SearchTop.route) {
        SearchTopScreen(
            onSearch = { navController.openSearchResult(it) }
        )
    }

    // お気に入りリスト
    composable(BottomNavigationScreen.Favorite.route) {
        FavoriteListScreen(navController = navController)
    }

    // 検索結果
    composable(
        route = SearchResult.routeWithArgs,
        arguments = SearchResult.argument
    ) { navBackStackEntry ->
        val keyword = navBackStackEntry.arguments?.getString(SearchResult.keywordArgs)
        SearchResultRoute(arg = keyword)
    }
}

fun NavHostController.openSearchResult(keyword: String) = openScreen("${SearchResult.route}/$keyword")

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