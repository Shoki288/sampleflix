package com.example.sampleflix.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core_navigation.Screen.BottomNavigationScreen
import com.example.feature_favorite.compose.FavoriteListScreen
import com.example.feature_home.compose.HomeScreen
import com.example.feature_search.compose.SearchTopScreen
import com.example.sampleflix.compose.theme.AppTheme

class ComposeMainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val bottomNavigationScreens = listOf(
                    BottomNavigationScreen.Home,
                    BottomNavigationScreen.SearchTop,
                    BottomNavigationScreen.Favorite,
                )

                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDirection = navBackStackEntry?.destination
                            bottomNavigationScreens.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.icon),
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(text = screen.name) },
                                    selected = currentDirection?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavigationScreen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavigationScreen.Home.route) { HomeScreen(navController) }
                        composable(BottomNavigationScreen.SearchTop.route) {
                            SearchTopScreen(
                                navController
                            )
                        }
                        composable(BottomNavigationScreen.Favorite.route) {
                            FavoriteListScreen(
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}