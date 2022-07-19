package com.zjp.compose_unit.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.common.compose.CustomIndicator
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.HomeSections
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.route.unitNavGraph

@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(
                navController,
                tabs = arrayOf(HomeSections.COMPOSE, HomeSections.COLLECTION, HomeSections.PROFILE)
            ) {
                navController.navigate(it.route) {
                    popUpTo(HomeSections.COMPOSE.route)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.Debug.route)
            }) {
                Icon(painter = painterResource(id = R.drawable.debug), contentDescription = "debug")
            }
        }
    ) { innerPaddingModifier ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier
                .padding(innerPaddingModifier)

        ) {
            unitNavGraph(navController)
        }
    }
}


@Composable
fun BottomBar(
    navController: NavController,
    tabs: Array<HomeSections> = HomeSections.values(),
    onTabChange: (HomeSections) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Screen.Splash.route
    val routes = remember { tabs.map { it.route } }

    if (currentRoute in routes) {
        ProvideWindowInsets() {
            val navPaddingValues =
                rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars)
            BottomAppBar(Modifier.padding(navPaddingValues)) {
                TabRow(
                    selectedTabIndex = routes.indexOf(currentRoute),
                    indicator = {
                        CustomIndicator(it, selectIndex = routes.indexOf(currentRoute))
                    },
                    divider = {},
                ) {
                    tabs.forEachIndexed { _, homeSection ->
                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable(
                                    onClick = {
                                        onTabChange(homeSection)
                                    },
                                    indication = null,
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                )
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = homeSection.icon),
                                contentDescription = null
                            )
                            if (currentRoute == homeSection.route) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = homeSection.title.uppercase().substring(0..2))
                            }
                        }

                    }
                }
            }
        }
    }
}



