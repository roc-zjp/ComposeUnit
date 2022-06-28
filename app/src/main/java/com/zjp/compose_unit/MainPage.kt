package com.zjp.compose_unit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apkfuns.logutils.LogUtils
import com.zjp.compose_unit.common.BottomNavigationScreens
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.viewmodel.HomeUiState
import com.zjp.compose_unit.common.viewmodel.HomeViewModel

import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.core_database.model.Compose

@Composable
fun MainView(
    navController: NavController,
    onClick: (compose: Compose) -> Unit,
    toDeveloper: () -> Unit = {},
    homeViewModel: HomeViewModel = viewModel()
) {
    var currentTableName by remember {
        mutableStateOf(BottomNavigationScreens.Composes.tableName)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Composes Unit") },

                actions = {
                    IconButton(onClick = {
                        toDeveloper()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.debug),
                            contentDescription = "Home",
                        )
                    }

                }
            )
        },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

                BottomNavigationItem(icon = {
                    Icon(
                        painter = painterResource(id = BottomNavigationScreens.Composes.iconResId),
                        contentDescription = null
                    )
                },
                    label = { Text(BottomNavigationScreens.Composes.tableName) },
                    selected = currentTableName == BottomNavigationScreens.Composes.tableName,
                    onClick = {
                        currentTableName = BottomNavigationScreens.Composes.tableName

                    })

                Spacer(modifier = Modifier.width(100.dp))

                BottomNavigationItem(icon = {
                    Icon(
                        painter = painterResource(id = BottomNavigationScreens.Profile.iconResId),
                        contentDescription = null
                    )
                },
                    label = { Text(BottomNavigationScreens.Profile.tableName) },
                    selected = currentTableName == BottomNavigationScreens.Profile.tableName,
                    onClick = {
                        currentTableName = BottomNavigationScreens.Profile.tableName
                    })

            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                when (currentTableName) {
                    BottomNavigationScreens.Profile.tableName -> ProfileScreen()
                    BottomNavigationScreens.Composes.tableName -> ComposesScreen() { compose ->
                        navController.navigate(Screen.ComposeDetailScreen.route + "/" + compose.id)
                        LogUtils.d(Screen.ComposeDetailScreen.route + "/" + compose.id)
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(50),
                backgroundColor = Color.Magenta
            ) {
                Icon(Icons.Filled.Add, tint = Color.White, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Preview(name = "Dark Mode")
@Composable
fun ComposeItemPreview() {
    Compose_unitTheme() {
        var navController = rememberNavController()
        MainView(onClick = {
            navController.navigate(Screen.ComposeDetailScreen.route)
        }, navController = navController)
    }
}