package com.zjp.system_composes.system.containers


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *【selectedTabIndex】：当前选择的Index【Int】
 *【divider】：底部分割线【Function】
 *【indicator】：指示器，默认底下一横条【Function】
 */
@Composable
fun TableRowBase() {
    var tabPage by remember {
        mutableStateOf(TabPage.Home)
    }
    TabRow(
        selectedTabIndex = tabPage.ordinal,
        divider = {
            Divider(
                Modifier
                    .width(5.dp)
                    .background(Color.Cyan)
            )
        },
        indicator = { tabPositions ->
            CustomIndicator(tabPositions = tabPositions, tabPage = tabPage)
        }) {
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    tabPage = TabPage.Home
                })
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "home")
        }

        Row(
            modifier = Modifier
                .clickable(onClick = {
                    tabPage = TabPage.DELETE
                })
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "delete")
        }
    }
}

@Composable
fun ScrollTableRowBase() {
    var tabPage by remember {
        mutableStateOf(0)
    }
    ScrollableTabRow(
        selectedTabIndex = tabPage,
        divider = {
            Divider(
                Modifier
                    .width(5.dp)
                    .background(Color.Cyan)
            )
        },
    ) {
        repeat(10) {
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        tabPage = it
                    })
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "home")
            }
        }
    }
}

@Composable
fun TabBase() {
    var selected by remember {
        mutableStateOf(false)
    }
    Tab(
        selected = selected,
        onClick = {
            selected =! selected
        }
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "home")
        }

    }
}

@Composable
private fun CustomIndicator(
    tabPositions: List<TabPosition>,
    tabPage: TabPage
) {

//    val indicatorLeft = tabPositions[tabPage.ordinal].left
//    val indicatorRight = tabPositions[tabPage.ordinal].right
    val color = if (tabPage == TabPage.Home) Color.Red else Color.Gray

    val transition = updateTransition(
        tabPage,
        label = "Tab indicator"
    )
    val indicatorLeft by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessMedium)
        },
        label = "Indicator left"
    ) { page ->
        tabPositions[page.ordinal].left
    }
    val indicatorRight by transition.animateDp(
        transitionSpec = {
            if (TabPage.Home isTransitioningTo TabPage.DELETE) {
                // Indicator moves to the right
                // The right edge moves faster than the left edge.
                spring(stiffness = Spring.StiffnessMedium)
            } else {
                // Indicator moves to the left.
                // The right edge moves slower than the left edge.
                spring(stiffness = Spring.StiffnessVeryLow)
            }
        },
        label = "Indicator right"
    ) { page ->
        tabPositions[page.ordinal].right
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
                RoundedCornerShape(4.dp)
            )
    )
}

@Preview
@Composable
fun TabRowPreview() {
    TableRowBase()
}


private enum class TabPage {
    Home, DELETE
}