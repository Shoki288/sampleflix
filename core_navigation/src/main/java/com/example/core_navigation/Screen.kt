package com.example.core_navigation

import androidx.annotation.DrawableRes
import com.example.core_design.R

sealed class Screen(val route: String) {
    sealed class BottomNavigationScreen(route: String, val name: String, @DrawableRes val icon: Int): Screen(route = route) {
        object Home: BottomNavigationScreen(route = ROUTE_HOME, name = "ホーム", icon = R.drawable.ic_home)
        object SearchTop: BottomNavigationScreen(route = ROUTE_SEARCH_TOP, name = "検索", icon = R.drawable.ic_search)
        object Favorite: BottomNavigationScreen(route = ROUTE_FAVORITE, name = "お気に入り", icon = R.drawable.ic_no_favorite)
    }

    // TODO SimpleClassNameがいいかも
    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_SEARCH_TOP = "search_top"
        const val ROUTE_FAVORITE = "favorite"
    }
}