package com.zjp.compose_unit.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjp.common.shape.AppBarHeight
import com.zjp.common.shape.BottomAppBarCutShape
import com.zjp.compose_unit.route.HomeSections

@Composable
fun UnitBottomAppBar(
    modifier: Modifier = Modifier,
    onTabChange: (HomeSections) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        BottomAppBar(
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(AppBarHeight)
                .clip(BottomAppBarCutShape()),
        ) {
            BottomAppBarLeft(onTabChange)
            Spacer(modifier = Modifier.weight(1f))
            BottomAppBarRight(onTabChange)
        }
        FloatingActionButton(
            onClick = { onSearchClick() },
            shape = RoundedCornerShape(size = 50.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-AppBarHeight / 2))
        ) {
            Icon(
                painter = painterResource(id = com.zjp.common.R.drawable.search),
                contentDescription = "search"
            )
        }
    }
}

@Composable
fun BottomAppBarLeft(onTabChange: (HomeSections) -> Unit) {
    Box(
        modifier = Modifier
            .clip(shapeTe)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = borderWidth, end = borderWidth
                )
                .clip(RoundedCornerShape(topEnd = inlineRadius))
                .background(color = Color(0xff4699FB))
                .fillMaxHeight(),

            ) {
            TableRowItem(
                homeSection = HomeSections.COMPOSE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
            TableRowItem(
                homeSection = HomeSections.COLLECTION,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
        }
    }
}


@Composable
fun BottomAppBarRight(onTabChange: (HomeSections) -> Unit) {
    Box(
        modifier = Modifier
            .clip(shapeTs)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = borderWidth, start = borderWidth)
                .clip(RoundedCornerShape(topStart = inlineRadius))
                .background(color = Color(0xff4699FB))
                .fillMaxHeight(),
        ) {
            TableRowItem(
                homeSection = HomeSections.ARTICLE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
            TableRowItem(
                homeSection = HomeSections.PROFILE,
                currentRoute = "",
                modifier = Modifier.width(itemWidth),
                onTabChange = onTabChange
            )
        }
    }

}


@Composable
fun TableRowItem(
    homeSection: HomeSections,
    currentRoute: String,
    modifier: Modifier = Modifier,
    onTabChange: (HomeSections) -> Unit
) {
    Row(
        modifier = modifier
            .padding(
                top = outlineRadius, bottom = outlineRadius
            )
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

val shapeTe = RoundedCornerShape(topEnd = 10.dp)
val shapeTs = RoundedCornerShape(topStart = 10.dp)
val itemWidth = 60.dp
private val outlineRadius = 10.dp
private val inlineRadius = 7.dp
private val borderWidth = 3.dp


@Preview
@Composable
fun BottomBarPreview() {
    UnitBottomAppBar()
}


//
//@Composable
//fun UnitBottomAppBar() {
//    ProvideWindowInsets() {
//        val navPaddingValues =
//            rememberInsetsPaddingValues(LocalWindowInsets.current.navigationBars)
//        BottomAppBar(
//            cutoutShape = CircleShape,
//            contentPadding = PaddingValues(0.dp),
//            modifier = Modifier
//                .padding(navPaddingValues)
//                .height(56.dp),
//        ) {
//            BottomAppBarLeft()
//            Spacer(modifier = Modifier.weight(1f))
//            BottomAppBarRight()
//        }
//    }
//}
//
//@Composable
//fun BottomAppBarLeft() {
//    Box(
//        modifier = Modifier
//            .clip(shapeTe)
//            .background(Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(
//                    top = 3.dp, end = 3.dp
//                )
//                .clip(RoundedCornerShape(topEnd = 7.dp))
//                .background(color = Color(0xff4699FB))
//                .fillMaxHeight(),
//
//            ) {
//            TableRowItem(
//                homeSection = HomeSections.COMPOSE,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//            TableRowItem(
//                homeSection = HomeSections.COLLECTION,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//        }
//    }
//}
//
//
//@Composable
//fun BottomAppBarRight() {
//    Box(
//        modifier = Modifier
//            .clip(shapeTs)
//            .background(Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(top = 3.dp, start = 3.dp)
//                .clip(RoundedCornerShape(topStart = 7.dp))
//                .background(color = Color(0xff4699FB))
//                .fillMaxHeight(),
//        ) {
//            TableRowItem(
//                homeSection = HomeSections.COMPOSE,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//            TableRowItem(
//                homeSection = HomeSections.COLLECTION,
//                currentRoute = "",
//                modifier = Modifier.width(itemWidth)
//            ) {
//
//            }
//        }
//    }
//
//}
//
//@Composable
//fun TableRowItem(
//    homeSection: HomeSections,
//    currentRoute: String,
//    modifier: Modifier = Modifier,
//    onTabChange: (HomeSections) -> Unit
//) {
//    Row(
//        modifier = modifier
//            .padding(
//                top = 10.dp, bottom = 10.dp
//            )
//            .clickable(
//                onClick = {
//                    onTabChange(homeSection)
//                },
//                indication = null,
//                interactionSource = remember {
//                    MutableInteractionSource()
//                }
//            )
//            .padding(4.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(id = homeSection.icon),
//            contentDescription = null
//        )
//        if (currentRoute == homeSection.route) {
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(text = homeSection.title.uppercase().substring(0..2))
//        }
//    }
//}
//
//val shapeTe = RoundedCornerShape(topEnd = 10.dp)
//val shapeTs = RoundedCornerShape(topStart = 10.dp)
//val itemWidth = 60.dp
