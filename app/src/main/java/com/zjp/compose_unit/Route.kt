package com.zjp.compose_unit

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.viewmodel.DbViewModel
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.compose.ComposeDetailPage


@Composable
fun App(dbViewModel: DbViewModel) {
    val navController = rememberNavController()
    val shareViewModel: ShareViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        // 给FirstPage可组合项指定路径
        composable(Screen.Main.route) { MainPage(navController, shareViewModel, dbViewModel) }
        composable(Screen.ComposeDetailScreen.route) {
            ComposeDetailPage(
                viewModel = shareViewModel,
                dbViewModel = dbViewModel,
                navController = navController
            )
        }
    }

}

@Composable
fun MainPage(navController: NavController, viewModel: ShareViewModel, dbViewModel: DbViewModel) {
    MainView(onClick = {
        viewModel.addCompose(it)
        navController.navigate(Screen.ComposeDetailScreen.route)
    }, viewModel, dbViewModel)
}




