package com.example.sampleflix.compose.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.core_design.R
import com.example.entity.BookInfo
import com.example.entity.BookInfoListResponse
import com.example.extension.createCustomNavType

interface SampleDestination {
    val route: String
}

interface BottomNavigationScreen : SampleDestination {
    val name: String
    val icon: Int

    object Home : BottomNavigationScreen {
        override val route: String = "home"
        override val name = "ホーム"
        override val icon = R.drawable.ic_home
    }
    object SearchTop : BottomNavigationScreen {
        override val route: String = "search_top"
        override val name = "検索"
        override val icon = R.drawable.ic_search
    }
    object Favorite : BottomNavigationScreen {
        override val route: String = "favorite"
        override val name = "お気に入り"
        override val icon = R.drawable.ic_no_favorite
    }
}

object SearchResult : SampleDestination {
    override val route = "search_result"
    const val keywordArgs = "keyword"
    val routeWithArgs = "$route/{$keywordArgs}"
    val argument = listOf(navArgument(keywordArgs) { type = NavType.StringType })
}

object BookDetail: SampleDestination {
    override val route = "detail"
    private const val bookInfoArgs = "book_info"
    val routeWithArgs = "$route/{$bookInfoArgs}"
    val argument = listOf(navArgument(bookInfoArgs) { type = createCustomNavType<BookInfo>() })
}

object Recommend : SampleDestination {
    override val route = "recommend"
    const val titleArgs = "title"
    const val bookInfoArgs = "book_info"
    val routeWithArgs = "$route/{$titleArgs}/{$bookInfoArgs}"
    val argument = listOf(
        navArgument(titleArgs) { type = NavType.StringType },
        navArgument(bookInfoArgs) { type = createCustomNavType<BookInfoListResponse>() }
    )
}