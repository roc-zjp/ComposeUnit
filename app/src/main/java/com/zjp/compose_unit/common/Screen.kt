package com.zjp.compose_unit.common

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object TextDetailScreen : Screen(route = "text_detail_screen")
    object TextFieldDetailScreen : Screen(route = "text_field_detail_screen")
    object ComposeDetailScreen : Screen(route = "compose_detail_screen")
}