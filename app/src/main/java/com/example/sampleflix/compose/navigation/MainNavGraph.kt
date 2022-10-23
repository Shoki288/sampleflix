package com.example.sampleflix.compose.navigation

import androidx.annotation.DrawableRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.core_design.R
import com.example.feature_favorite.compose.FavoriteListScreen
import com.example.feature_home.compose.HomeScreen
import com.example.feature_search.compose.SearchTopScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(BottomNavigationScreen.Home.route) { HomeScreen(navController = navController) }
    composable(BottomNavigationScreen.SearchTop.route) { SearchTopScreen(navController = navController) }
    composable(BottomNavigationScreen.Favorite.route) { FavoriteListScreen(navController = navController) }
}

internal sealed class BottomNavigationScreen(val route: String, val name: String, @DrawableRes val icon: Int) {
    object Home : BottomNavigationScreen(route = "home", name = "ホーム", icon = R.drawable.ic_home)
    object SearchTop : BottomNavigationScreen(route = "search_top", name = "検索", icon = R.drawable.ic_search)
    object Favorite : BottomNavigationScreen(route = "favorite", name = "お気に入り", icon = R.drawable.ic_no_favorite)
}