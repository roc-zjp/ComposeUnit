package com.zjp.compose_unit.common

import com.zjp.compose_unit.R
import com.zjp.core_database.model.Compose

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object Profile : Screen(route = "profile_screen")
    object TextDetailScreen : Screen(route = "text_detail_screen")
    object TextFieldDetailScreen : Screen(route = "text_field_detail_screen")
    object ComposeDetailScreen : Screen(route = "compose_detail_screen")
    object Debug : Screen(route = "debug_screen")

    fun createComposeDetailRoute(compose: Compose): String {
        return "${ComposeDetailScreen.route}" +
                "/${compose.id}" +
                "/${compose.name}" +
                "/${compose.nameCN}" +
                "/${compose.deprecated}" +
                "/${compose.family}" +
                "/${compose.level}" +
                "/${compose.linkWidget}" +
                "/${compose.info}"
    }

    fun composeDetailRoute(): String {
        return "${ComposeDetailScreen.route}/{id}/{name}/{nameCN}/{deprecated}/{family}/{level}/{linkWidget}/{info}"
    }
}


sealed class BottomNavigationScreens(val tableName: String, val iconResId: Int) {
    object Composes :
        BottomNavigationScreens(tableName = "Composes", iconResId = R.drawable.widget)

    object Profile :
        BottomNavigationScreens(tableName = "Profile", iconResId = R.drawable.profile_light)

}


