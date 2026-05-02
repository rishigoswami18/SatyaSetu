package com.satyasetu.app.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.satyasetu.app.ui.auth.AuthViewModel
import com.satyasetu.app.ui.navigation.BottomNavBar
import com.satyasetu.app.ui.navigation.NavGraph
import com.satyasetu.app.ui.navigation.Screen
import com.satyasetu.app.ui.theme.SatyaSetuTheme
import com.satyasetu.app.util.LocaleManager
import com.satyasetu.app.util.localeDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localeManager: LocaleManager

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            // Read language synchronously from DataStore before activity creation
            val language = runBlocking {
                try {
                    val dataStoreName = "locale_prefs"
                    val languageKey = androidx.datastore.preferences.core.stringPreferencesKey("app_language")
                    
                    // Access DataStore manually since Hilt isn't ready
                    val context = newBase.applicationContext ?: newBase
                    val data = context.localeDataStore.data.first()
                    data[languageKey] ?: "hi"
                } catch (e: Exception) {
                    "hi"
                }
            }
            
            val locale = java.util.Locale(language)
            java.util.Locale.setDefault(locale)
            
            val config = android.content.res.Configuration(newBase.resources.configuration)
            config.setLocale(locale)
            
            super.attachBaseContext(newBase.createConfigurationContext(config))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SatyaSetuTheme(darkTheme = true) {
                SatyaSetuApp(localeManager)
            }
        }
    }
}

@Composable
fun SatyaSetuApp(localeManager: LocaleManager) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in Screen.bottomNavItems.map { it.route }

    // Observe language changes and recreate if different from current configuration
    val context = androidx.compose.ui.platform.LocalContext.current
    val initialLang = remember { context.resources.configuration.locales[0].language }
    val currentLanguage by localeManager.currentLanguage.collectAsStateWithLifecycle(initialValue = initialLang)
    
    LaunchedEffect(currentLanguage) {
        if (currentLanguage != initialLang) {
            (context as? android.app.Activity)?.recreate()
        }
    }

    LaunchedEffect(isLoggedIn) {
        val destination = if (isLoggedIn) Screen.VillageFeed.route else Screen.Login.route
        // Only navigate manually if we're already on a route (not initial load) 
        // and the route is different (e.g. login/logout event)
        if (currentRoute != null && currentRoute != destination) {
            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavBar(navController = navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            isLoggedIn = isLoggedIn,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}
