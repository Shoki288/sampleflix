package com.example.sampleflix.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core_design.theme.AppTheme
import com.example.sampleflix.compose.navigation.BottomNavigationScreen
import com.example.sampleflix.compose.navigation.mainNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { GlobalNavigation(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        mainNavGraph(navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun GlobalNavigation(
    navController: NavHostController,
) {
    val bottomNavigationScreens = listOf(BottomNavigationScreen.Home, BottomNavigationScreen.SearchTop, BottomNavigationScreen.Favorite)

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.zIndex(2F)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDirection = navBackStackEntry?.destination
        bottomNavigationScreens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = null,
                        tint = if (currentDirection?.hierarchy?.any { it.route == screen.route } == true) Color.Black else Color.Gray.copy(alpha = 0.7f)
                    )
                },
                label = { Text(text = screen.name) },
                selected = currentDirection?.hierarchy?.any { it.route == screen.route } == true,
                selectedContentColor = MaterialTheme.colors.onBackground,
                alwaysShowLabel = false,
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