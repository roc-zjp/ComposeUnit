package com.zjp.compose_unit

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.viewmodel.HomeViewModel
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.compose.ComposeDetailPage


@Composable
fun App(dbViewModel: DbViewModel) {
    val navController = rememberNavController()
    val shareViewModel: ShareViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        // 给FirstPage可组合项指定路径
        composable(Screen.Main.route) { MainPage(navController) }
        composable(Screen.Debug.route) { DeveloperPage(navController) }
        composable(Screen.Profile.route) { ProfilePage(navController) }
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
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    MainView(
        navController = navController,
        onClick = {
            navController.navigate(Screen.ComposeDetailScreen.route + "/${it.id}")
        }, toDeveloper = {
            navController.navigate(Screen.Debug.route)
        },
        homeViewModel
    )
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




