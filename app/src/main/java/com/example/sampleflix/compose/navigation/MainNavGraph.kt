package com.example.sampleflix.compose.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature_favorite.compose.FavoriteListScreen
import com.example.feature_home.compose.HomeScreen
import com.example.feature_search.compose.SearchTopScreen
import com.example.feature_search_result.compose.SearchResultScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    // ホーム画面
    composable(BottomNavigationScreen.Home.route) {
        HomeScreen(navController = navController)
    }
    // 検索TOP
    composable(BottomNavigationScreen.SearchTop.route) {
        SearchTopScreen(
            onSearch = { navController.navigationSearchResult(it) }
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
        SearchResultScreen(arg = keyword)
    }
}

fun NavHostController.navigationSearchResult(keyword: String) {
    this.navigate("${SearchResult.route}/$keyword") {
        popUpTo(this@navigationSearchResult.graph.findStartDestination().id) {
            saveState = true
        }
        restoreState = true
    }
}