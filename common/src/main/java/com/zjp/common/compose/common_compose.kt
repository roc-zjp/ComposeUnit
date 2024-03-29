package com.zjp.common.compose

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
) {

    TopAppBar(
        title = title,
        modifier = Modifier
            .background(backgroundColor)
            .then(modifier),
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)

    )
}


@Composable
fun CustomIndicator(
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


@Composable
fun rememberNestedScrollConnection(onOffsetChanged: (Float) -> Unit, appBarHeight: Float) =
    remember {
        var currentHeight = appBarHeight
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                Log.d("AVAILABLE", "$available")
                currentHeight = (currentHeight + available.y).coerceIn(
                    minimumValue = 0f,
                    maximumValue = appBarHeight
                )
                return if (abs(currentHeight) == appBarHeight || abs(currentHeight) == 0f) {
                    super.onPreScroll(available, source)
                } else {
                    onOffsetChanged(currentHeight)
                    available
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (available.y < 0) {
                    onOffsetChanged(0f)
                } else {
                    onOffsetChanged(appBarHeight)
                }
                return super.onPreFling(available)
            }
        }
    }


//@Composable
//fun FoldAppbar(
//    minHeightDp: Dp,
//    maxHeightDp: Dp,
//    contentScrollState: LazyGridState = rememberLazyGridState(),
//    modifier: Modifier = Modifier,
//    appBar: @Composable BoxScope.(progress: Float) -> Unit,
//    content: @Composable (appBarHeightDp: Dp) -> Unit
//) {
//    val minHeightPx = with(LocalDensity.current) { minHeightDp.roundToPx().toFloat() }
//    val maxHeightPx = with(LocalDensity.current) { maxHeightDp.roundToPx().toFloat() }
//    var toolbarOffsetHeightPx by remember { mutableStateOf(maxHeightPx) }
//
//    val progress =
//        (maxHeightPx - toolbarOffsetHeightPx) / (maxHeightPx - minHeightPx).toFloat()
//    val nestedScrollConnection = remember {
//        object : NestedScrollConnection {
//            override fun onPreScroll(
//                available: Offset,
//                source: NestedScrollSource
//            ): Offset {
//                val delta = available.y
//                if (contentScrollState.firstVisibleItemIndex == 0 && contentScrollState.firstVisibleItemScrollOffset == 0) {
//                    val newOffset = toolbarOffsetHeightPx + delta
//                    val newHeight = newOffset.coerceIn(minHeightPx, maxHeightPx)
//                    val consumed = newHeight - toolbarOffsetHeightPx
//                    toolbarOffsetHeightPx = newHeight
//                    return Offset(0f, consumed)
//                }
//                Log.d(
//                    "FoldAppbar",
//                    "delta=${delta},toolbarOffsetHeightPx=${toolbarOffsetHeightPx},firstVisibleItemIndex=${contentScrollState.firstVisibleItemIndex},firstVisibleItemScrollOffset=${contentScrollState.firstVisibleItemScrollOffset}"
//                )
//                return Offset.Zero
//            }
//
//
//            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
//                Log.d(
//                    "Fling",
//                    "消费了的速度：${consumed.y},可消费的速度：${available.y}"
//                )
//                val delta = available.y / 10f
//                val newOffset = toolbarOffsetHeightPx + delta
//                val newHeight = newOffset.coerceIn(minHeightPx, maxHeightPx)
//                val consumed = newHeight - toolbarOffsetHeightPx
//                toolbarOffsetHeightPx = newHeight
//                return Velocity(0f, consumed)
//            }
//
//            override suspend fun onPreFling(available: Velocity): Velocity {
//                Log.d(
//                    "Fling",
//                    "初始速度：${available.y}"
//                )
//                return Velocity.Zero
//            }
//        }
//    }
//    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
//        val animatedHeight by animateDpAsState(
//            targetValue = with(LocalDensity.current) { toolbarOffsetHeightPx.toDp() }
//        )
//        content(with(LocalDensity.current) { toolbarOffsetHeightPx.toDp() })
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(animatedHeight)
//        ) {
//            appBar(progress)
//        }
//    }
//}


@Composable
fun FoldAppbar(
    maxHeightDp: Dp = 200.dp,
    minHeightDp: Dp = 0.dp,
    modifier: Modifier = Modifier,
    appbar: @Composable (progress: Float) -> Unit,
    content: @Composable (height: Dp) -> Unit
) {
    val density = LocalDensity.current
    var topHeight by remember {
        mutableStateOf(maxHeightDp)
    }
    var scrollHeight = remember {
        0f
    }

    var progress = remember {
        0.0f
    }

    var maxHeight = remember {
        with(density) { maxHeightDp.toPx().toInt() }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = consumed.y

                scrollHeight += delta
                val newHeight = with(density) { (maxHeight + scrollHeight).toDp() }
                topHeight = newHeight.coerceIn(minHeightDp, maxHeightDp)
                progress = (maxHeightDp - topHeight) / (maxHeightDp - minHeightDp)
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier.nestedScroll(nestedScrollConnection)) {
        content(maxHeightDp)
        TopBar(minHeightDp, maxHeightDp, progress, topHeight) {
            appbar(progress)
        }
    }
}

@Composable
fun TopBar(
    minHeightDp: Dp,
    maxHeightDp: Dp,
    progress: Float,
    height: Dp,
    content: @Composable TopScope.() -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        TopScopeImpl(minHeightDp, maxHeightDp, progress).content()
    }
}


@Stable
interface TopScope {

    val minHeight: Dp

    val maxHeight: Dp

    val progress: Float
}


data class TopScopeImpl(
    override val minHeight: Dp,
    override val maxHeight: Dp,
    override val progress: Float
) : TopScope {

}

