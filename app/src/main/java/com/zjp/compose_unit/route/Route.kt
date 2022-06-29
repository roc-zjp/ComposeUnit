package com.zjp.compose_unit.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zjp.compose_unit.ui.developer.DeveloperScreen
import com.zjp.compose_unit.MainView
import com.zjp.compose_unit.ui.SplashView
import com.zjp.compose_unit.ui.detail.ComposeDetailPage
import com.zjp.compose_unit.ui.home.ProfileScreen


@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        // 给FirstPage可组合项指定路径
        composable(Screen.Main.route) { MainPage(navController) }
        composable(Screen.Debug.route) { DeveloperPage(navController) }
        composable(Screen.Profile.route) { ProfilePage(navController) }
        composable(Screen.Splash.route) { SplashPage(navController) }
        composable(
            Screen.ComposeDetailScreen.route + "/{composeId}",
            arguments = listOf(navArgument("composeId") { type = NavType.IntType })
        ) {
            var composeId = it.arguments?.getInt("composeId")
            ComposeDetailPage(
                navController = navController,
                composeId = composeId!!
            )
        }
    }

}

@Composable
fun MainPage(
    navController: NavController
) {
    MainView(
        onClick = {
            navController.navigate(Screen.ComposeDetailScreen.createComposeDetailRoute(it))
        },
        toDeveloper = {
            navController.navigate(Screen.Debug.route)
        })
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
fun ProfilePage(
    navController: NavController,
) {
    ProfileScreen()
}

@Composable
fun SplashPage(
    navController: NavController,
) {
    SplashView() {
        navController.navigate(Screen.Main.route) {
            launchSingleTop = true
            navController.popBackStack()
        }
    }
}




