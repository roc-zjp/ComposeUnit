package com.zjp.compose_unit.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.route.HomeSections
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.route.unitNavGraph


@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController) {
                navController.navigate(it.route) {
                    popUpTo(HomeSections.COMPOSE.route)
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Splash.route) {
            unitNavGraph(navController)
        }
    }
}



@Composable
fun BottomBar(
    navController: NavController,
    onTabChange: (HomeSections) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Screen.Splash.route
    val routes = remember { HomeSections.values().map { it.route } }
    if (currentRoute in routes) {
        BottomAppBar() {
            TabRow(selectedTabIndex = routes.indexOf(currentRoute),
                indicator = {
                    CustomIndicator(it, selectIndex = routes.indexOf(currentRoute))
                },
                divider = {}
            ) {
                HomeSections.values().forEachIndexed { _, bottomNavigationScreen ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable(onClick = {
                                onTabChange(bottomNavigationScreen)
                            })
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = bottomNavigationScreen.icon),
                            contentDescription = null
                        )
                        if (currentRoute == bottomNavigationScreen.route) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = bottomNavigationScreen.title.substring(0..2))
                        }
                    }

                }
            }
        }
    }


}


@Composable
private fun CustomIndicator(
    tabPositions: List<TabPosition>,
    selectIndex: Int
) {
    val color = Color.White
    val transition = updateTransition(
        selectIndex,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessVeryLow)
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessVeryLow)
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page].right
    }
    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorLeft)
            .width(indicatorRight - indicatorLeft)
            .padding(4.dp)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, color),
                CircleShape
            )
    )
}
