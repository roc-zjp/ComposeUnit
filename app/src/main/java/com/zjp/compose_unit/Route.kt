package com.zjp.compose_unit

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.compose.text.TextDetailView
import com.zjp.compose_unit.compose.text.TextFieldDetailView


@Composable
fun App() {
    val navController = rememberNavController()
    val shareViewModel: ShareViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        // 给FirstPage可组合项指定路径
        composable(Screen.Main.route) { MainPage(navController, shareViewModel) }
        // 给SecondPage可组合项指定路径
        composable(
            Screen.TextDetailScreen.route,
        ) {
            TextDetailPage(
                navController,
                shareViewModel
            )
        }
        composable(Screen.TextFieldDetailScreen.route) { TextFieldDetailPage(navController) }
    }

}

@Composable
fun MainPage(navController: NavController, viewModel: ShareViewModel) {
    MainView(onClick = {
        viewModel.addItem(it)
        navController.navigate(it.detailPage)
    }, viewModel)
}


@Composable
fun TextDetailPage(navController: NavController, viewModel: ShareViewModel) {
    TextDetailView(navController, viewModel)
}

@Composable
fun TextFieldDetailPage(navController: NavController) {
    TextFieldDetailView(navController)
}


