package com.zjp.compose_unit.route

import com.zjp.compose_unit.R
import com.zjp.core_database.model.Compose

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object Profile : Screen(route = "profile_screen")
    object ComposeDetailScreen : Screen(route = "compose_detail_screen")
    object Debug : Screen(route = "debug_screen")

    fun createComposeDetailRoute(compose: Compose): String {
        return "${Screen.ComposeDetailScreen.route}/${compose.id}"
    }

    fun composeDetailRoute(): String {
        return "${ComposeDetailScreen.route}/{composeId}"
    }
}

sealed class BottomNavigationScreens(val tableName: String, val iconResId: Int) {
    object Composes :
        BottomNavigationScreens(tableName = "Composes", iconResId = R.drawable.widget)

    object Profile :
        BottomNavigationScreens(tableName = "Profile", iconResId = R.drawable.profile_light)

}


