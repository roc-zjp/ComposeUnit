package com.zjp.compose_unit.route

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.zjp.collection.ui.CollectionScreen
import com.zjp.compose_unit.ui.SplashView
import com.zjp.compose_unit.ui.detail.ComposeDetailPage
import com.zjp.compose_unit.ui.developer.DeveloperScreen
import com.zjp.compose_unit.ui.home.CollectionPage
import com.zjp.compose_unit.ui.home.ComposesScreen
import com.zjp.compose_unit.ui.home.ProfileScreen
import com.zjp.compose_unit.ui.home.SamplesPage


fun NavGraphBuilder.unitNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Screen.Home.route,
        startDestination = HomeSections.COMPOSE.route
    ) {
        addHomeGraph(navController = navController)
    }
    composable(
        Screen.ComposeDetailScreen.route + "/{composeId}",
        arguments = listOf(navArgument("composeId") { type = NavType.IntType })
    ) {
        val composeId = it.arguments?.getInt("composeId")
        ComposeDetailPage(
            composeId = composeId!!,
            goBack = { navController.popBackStack() },
            goHome = {
                navController.popBackStack(HomeSections.COMPOSE.route, false)
            },
            toComposeDetail = { id ->
                navController.navigate(Screen.ComposeDetailScreen.createComposeDetailRoute(id))
            }
        )
    }
    composable(
        Screen.Collection.route + "/{composeId}",
        arguments = listOf(navArgument("composeId") { type = NavType.IntType })
    ) {
        val composeId = it.arguments?.getInt("composeId")
        ComposeDetailPage(
            composeId = composeId!!,
            goBack = { navController.popBackStack() },
            goHome = {
                navController.popBackStack(HomeSections.COMPOSE.route, false)
            },
            toComposeDetail = { id ->
                navController.navigate(Screen.ComposeDetailScreen.createComposeDetailRoute(id))
            }
        )
    }
    composable(
        Screen.Splash.route,
    ) {
        SplashPage(navController = navController)
    }
    composable(
        Screen.Debug.route,
    ) {
        DeveloperPage(navController = navController)
    }
}


fun NavGraphBuilder.addHomeGraph(
    navController: NavController
) {
    composable(HomeSections.COMPOSE.route) { from ->
        ComposesScreen(onClick = {
            navController.navigate(Screen.ComposeDetailScreen.createComposeDetailRoute(it.id))
        })
    }
    composable(HomeSections.COLLECTION.route) { from ->
        CollectionScreen()
    }
    composable(HomeSections.SAMPLE.route) { from ->
        SamplesPage()
    }
    composable(HomeSections.PROFILE.route) {
        ProfilePage() { route ->
            navController.navigate(route)
        }
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
    ProfileScreen()
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




