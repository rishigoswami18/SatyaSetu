package com.satyasetu.app.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.satyasetu.app.ui.auth.LoginScreen
import com.satyasetu.app.ui.auth.RegisterScreen
import com.satyasetu.app.ui.chatbot.ChatScreen
import com.satyasetu.app.ui.feed.VideoFeedScreen
import com.satyasetu.app.ui.learning.LearningScreen
import com.satyasetu.app.ui.learning.QuizScreen
import com.satyasetu.app.ui.myths.MythFactScreen
import com.satyasetu.app.ui.report.ReportScreen
import com.satyasetu.app.ui.tracker.TrackerScreen
import com.satyasetu.app.ui.admin.AdminDashboardScreen
import com.satyasetu.app.ui.village.VillageFeedScreen
import com.satyasetu.app.ui.village.MapLocationPickerScreen
import com.satyasetu.app.ui.schemes.SchemesScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Screen.VillageFeed.route else Screen.Login.route,
        modifier = modifier
    ) {
        // Auth
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.VillageFeed.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.VillageFeed.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Hyperlocal Village
        composable(Screen.VillageFeed.route) {
            VillageFeedScreen(
                onNavigateToMap = { navController.navigate(Screen.MapPicker.route) }
            )
        }
        composable(Screen.MapPicker.route) {
            MapLocationPickerScreen(
                onNavigateBack = { navController.popBackStack() },
                onLocationConfirmed = {
                    navController.navigate(Screen.VillageFeed.route) {
                        popUpTo(Screen.MapPicker.route) { inclusive = true }
                    }
                }
            )
        }

        // Main screens
        composable(Screen.Feed.route) {
            VideoFeedScreen(
                onNavigateToReport = { navController.navigate(Screen.Report.route) }
            )
        }
        composable(Screen.SachYaGalat.route) {
            MythFactScreen()
        }
        composable(Screen.Learning.route) {
            LearningScreen(
                onNavigateToQuiz = { lessonId ->
                    navController.navigate(Screen.Quiz.createRoute(lessonId))
                }
            )
        }
        composable(Screen.Tracker.route) {
            TrackerScreen()
        }
        composable(Screen.Chat.route) {
            ChatScreen()
        }
        composable(Screen.Schemes.route) {
            SchemesScreen(onNavigateBack = { navController.popBackStack() })
        }

        // Secondary screens
        composable(Screen.Report.route) {
            ReportScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(
            route = Screen.Quiz.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
            QuizScreen(
                lessonId = lessonId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Admin
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = NavigationBarDefaults.Elevation
    ) {
        Screen.bottomNavItems.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.VillageFeed.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) screen.selectedIcon!! else screen.unselectedIcon!!,
                        contentDescription = if (screen.resourceId != null) {
                            androidx.compose.ui.res.stringResource(id = screen.resourceId)
                        } else {
                            screen.titleHindi
                        }
                    )
                },
                label = {
                    Text(
                        text = if (screen.resourceId != null) {
                            androidx.compose.ui.res.stringResource(id = screen.resourceId)
                        } else {
                            screen.titleHindi
                        },
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                )
            )
        }
    }
}
