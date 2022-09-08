package com.zjp.compose_unit.route

import com.zjp.compose_unit.R

sealed class Screen(val route: String) {
    object ComposeDetailScreen : Screen(route = "compose_detail_screen")
    object Home : Screen(route = "home_screen")
    object Search : Screen(route = "search_screen")
    object Debug : Screen(route = "debug_screen")
    object Splash : Screen(route = "splash_screen")
    object ThemeSetting : Screen(route = "theme_setting_screen")
    object AppSetting : Screen(route = "app_setting_screen")
    object FontSetting : Screen(route = "font_setting_screen")
    object AboutMe : Screen(route = "about_me_screen")
    object AboutApp : Screen(route = "about_app_screen")
    object CollectionDetailScreen : Screen(route = "collection_detail_screen")
    object ArticleDetailScreen : Screen(route = "article_detail_screen")

    fun createComposeDetailRoute(id: Int): String {
        return "${ComposeDetailScreen.route}/${id}"
    }

    fun createCollectionDetailRoute(id: Int): String {
        return "${CollectionDetailScreen.route}/${id}"
    }
}

//sealed class BottomNavigationScreens(val tableName: String, val iconResId: Int) {
//    object Composes :
//        BottomNavigationScreens(tableName = "Composes", iconResId = R.drawable.widget)
//
//    object Collection :
//        BottomNavigationScreens(
//            tableName = "Collection",
//            iconResId = R.drawable.ic_outline_collections_24
//        )
//
//    object FloatButton :
//        BottomNavigationScreens(
//            tableName = "FloatButton",
//            iconResId = R.drawable.ic_outline_collections_24
//        )
//
//    object Samples :
//        BottomNavigationScreens(tableName = "Samples", iconResId = R.drawable.sample)
//
//    object Profile :
//        BottomNavigationScreens(tableName = "Profile", iconResId = R.drawable.profile_light)
//
//}


enum class HomeSections(
    val title: String,
    val icon: Int,
    val route: String
) {
    COMPOSE("compose", R.drawable.widget, "home_screen/compose"),
    COLLECTION("collection", R.drawable.subject, "home_screen/collection"),
    SAMPLE("sample", R.drawable.sample, "home_screen/sample"),
    PROFILE("profile", R.drawable.profile_light, "home_screen/profile"),
    ARTICLE("article", R.drawable.article_line, "home_screen/article")
}



