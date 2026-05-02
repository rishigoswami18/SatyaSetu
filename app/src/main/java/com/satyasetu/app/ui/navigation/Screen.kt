package com.satyasetu.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Navigation routes for the app
 */
sealed class Screen(
    val route: String,
    val title: String,
    val titleHindi: String,
    val resourceId: Int? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    // Auth
    data object Login : Screen("login", "Login", "लॉगिन")
    data object Register : Screen("register", "Register", "रजिस्टर")

    // Main tabs
    data object Feed : Screen("feed", "Feed", "वीडियो", com.satyasetu.app.R.string.nav_feed,
        Icons.Filled.PlayCircle, Icons.Outlined.PlayCircle)
    data object SachYaGalat : Screen("sach_ya_galat", "Sach Ya Galat", "सच या गलत", com.satyasetu.app.R.string.nav_myths,
        Icons.Filled.FactCheck, Icons.Outlined.FactCheck)
    data object Learning : Screen("learning", "Learn", "सीखें", com.satyasetu.app.R.string.nav_learn,
        Icons.Filled.School, Icons.Outlined.School)
    data object Tracker : Screen("tracker", "Tracker", "ट्रैकर", com.satyasetu.app.R.string.nav_tracker,
        Icons.Filled.Assessment, Icons.Outlined.Assessment)
    data object Chat : Screen("chat", "Chat", "सहायक", com.satyasetu.app.R.string.nav_chat,
        Icons.Filled.SmartToy, Icons.Outlined.SmartToy)
    data object Schemes : Screen("schemes", "Schemes", "योजनाएँ", com.satyasetu.app.R.string.nav_schemes,
        Icons.Filled.AccountBalance, Icons.Outlined.AccountBalance)

    // Secondary
    data object Report : Screen("report", "Report", "रिपोर्ट", com.satyasetu.app.R.string.report_title)
    data object Quiz : Screen("quiz/{lessonId}", "Quiz", "क्विज़") {
        fun createRoute(lessonId: String) = "quiz/$lessonId"
    }
    data object Profile : Screen("profile", "Profile", "प्रोफ़ाइल")
    data object AdminDashboard : Screen("admin", "Admin", "एडमिन")

    // Hyperlocal
    data object VillageFeed : Screen("village_feed", "Village", "मेरा गाँव", com.satyasetu.app.R.string.nav_village,
        Icons.Filled.Home, Icons.Outlined.Home)
    data object MapPicker : Screen("map_picker", "Map", "मैप")

    companion object {
        val bottomNavItems = listOf(VillageFeed, Feed, SachYaGalat, Schemes, Learning, Chat)
    }
}
