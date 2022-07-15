package com.zjp.compose_unit.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.compose_unit.route.HomeSections
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.route.unitNavGraph


@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController, tabs = arrayOf(HomeSections.COMPOSE, HomeSections.PROFILE)) {
                navController.navigate(it.route) {
                    popUpTo(HomeSections.COMPOSE.route)
                }
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

@Composable
fun UnitTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp
) {
    ProvideWindowInsets {
        val sbPaddingValues = rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars)
        TopAppBar(
            title,
            modifier = Modifier
                .background(backgroundColor)
                .padding(sbPaddingValues)
                .then(modifier),
            navigationIcon,
            actions,
            backgroundColor,
            contentColor,
            elevation
        )

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
