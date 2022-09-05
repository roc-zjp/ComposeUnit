package com.zjp.compose_unit.route

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.zjp.collection.ui.CollectionDetailPage
import com.zjp.collection.ui.CollectionPage
import com.zjp.compose_unit.ui.ArticlePage
import com.zjp.compose_unit.ui.SplashView
import com.zjp.compose_unit.ui.detail.ComposeDetailPage
import com.zjp.compose_unit.ui.developer.DeveloperScreen
import com.zjp.compose_unit.ui.home.ComposesScreen
import com.zjp.compose_unit.ui.home.SamplesPage
import com.zjp.compose_unit.ui.profile.*
import com.zjp.compose_unit.ui.search.SearchPage

fun NavGraphBuilder.unitNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = Screen.Home.route,
        startDestination = HomeSections.COMPOSE.route
    ) {
        addHomeGraph(navController = navController)
    }

    composable(
        Screen.ComposeDetailScreen.route + "/{composeId}",
        arguments = listOf(
            navArgument("composeId") { type = NavType.IntType })
    ) {
        val composeId = it.arguments?.getInt("composeId")
        ComposeDetailPage(
            composeId = composeId!!,
            goBack = { navController.popBackStack() },
            goHome = {
                navController.popBackStack(HomeSections.COMPOSE.route, false)
            },
            toComposeDetail = { id ->
                navController.navigate(
                    Screen.ComposeDetailScreen.createComposeDetailRoute(
                        id
                    )
                )
            }
        )
    }
    composable(
        Screen.CollectionDetailScreen.route + "/{composeId}",
        arguments = listOf(navArgument("composeId") { type = NavType.IntType })
    ) {
        val composeId = it.arguments?.getInt("composeId")
        CollectionDetailPage(
            composeId = composeId!!,
            goBack = { navController.popBackStack() },
            goHome = {
                navController.popBackStack(HomeSections.COMPOSE.route, false)
            },
        )
    }
    composable(
        Screen.Splash.route,
    ) {
        SplashPage(navController = navController)
    }
    composable(
        Screen.Search.route,
    ) {
        SearchPage(toComposeDetail = { id ->
            navController.navigate(
                Screen.ComposeDetailScreen.createComposeDetailRoute(
                    id
                )
            ) {
                navController.popBackStack()
            }
        }, onBack = {
            navController.popBackStack()
        })
    }
    composable(
        Screen.Debug.route,
    ) {
        DeveloperPage(navController = navController)
    }
    composable(
        Screen.ThemeSetting.route,
    ) {
        ThemeColorSettingScreen() {
            navController.popBackStack()
        }

    }
    composable(
        Screen.AppSetting.route,
    ) {
        AppSettingScreen(navigateToRoute = { route ->
            navController.navigate(route)
        }, goBack = {
            navController.popBackStack()
        })
    }
    composable(
        Screen.FontSetting.route,
    ) {
        FontSettingScreen() {
            navController.popBackStack()
        }

    }
    composable(
        Screen.AboutMe.route,
    ) {
        AboutMeScreen() {
            navController.popBackStack()
        }
    }
    composable(
        Screen.AboutApp.route,
    ) {
        AboutAppScreen() {
            navController.popBackStack()
        }
    }
}


fun NavGraphBuilder.addHomeGraph(
    navController: NavController
) {
    composable(HomeSections.COMPOSE.route) { _ ->
        ComposesScreen(onClick = {
            navController.navigate(
                Screen.ComposeDetailScreen.createComposeDetailRoute(
                    it.id

                )
            )
        })
    }
    composable(HomeSections.COLLECTION.route) { _ ->
        CollectionPage() {
            navController.navigate(
                Screen.ComposeDetailScreen.createCollectionDetailRoute(
                    it.id
                )
            )
        }

    }
    composable(HomeSections.SAMPLE.route) { _ ->
        SamplesPage()
    }
    composable(HomeSections.PROFILE.route) {
        ProfilePage() { route ->
            navController.navigate(route)
        }
    }
    composable(HomeSections.ARTICLE.route) {
        ArticlePage()
    }
}


@Composable
fun DeveloperPage(
    navController: NavController,
) {
    DeveloperScreen() {
        navController.popBackStack()
    }
}

@Composable
fun ProfilePage(navigateToRoute: (String) -> Unit) {
    ProfileScreen(navigateToRoute)
}

@Composable
fun SplashPage(
    navController: NavController,
) {
    SplashView() {
        navController.navigate(Screen.Home.route) {
            launchSingleTop = true
            navController.popBackStack()
        }
    }
}




