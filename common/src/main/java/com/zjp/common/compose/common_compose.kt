package com.zjp.common.compose

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.common.R
import kotlin.math.abs
import kotlin.math.roundToInt

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


@Composable
fun FoldAppbar(
    minHeightDp: Dp,
    maxHeightDp: Dp,
    contentScrollState: LazyGridState = rememberLazyGridState(),
    modifier: Modifier = Modifier,
    appBar: @Composable BoxScope.(progress: Float) -> Unit,
    content: LazyGridScope.() -> Unit
) {
    val minHeightPx = with(LocalDensity.current) { minHeightDp.roundToPx().toFloat() }
    val maxHeightPx = with(LocalDensity.current) { maxHeightDp.roundToPx().toFloat() }
    var toolbarOffsetHeightPx by remember { mutableStateOf(maxHeightPx) }

    val progress =
        (maxHeightPx - toolbarOffsetHeightPx) / (maxHeightPx - minHeightPx).toFloat()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                if (contentScrollState.firstVisibleItemIndex == 0 && contentScrollState.firstVisibleItemScrollOffset == 0) {
                    val newOffset = toolbarOffsetHeightPx + delta
                    val newHeight = newOffset.coerceIn(minHeightPx, maxHeightPx)
                    val consumed = newHeight - toolbarOffsetHeightPx
                    toolbarOffsetHeightPx = newHeight
                    return Offset(0f, consumed)
                }
                Log.d(
                    "FoldAppbar",
                    "delta=${delta},toolbarOffsetHeightPx=${toolbarOffsetHeightPx},firstVisibleItemIndex=${contentScrollState.firstVisibleItemIndex},firstVisibleItemScrollOffset=${contentScrollState.firstVisibleItemScrollOffset}"
                )
                return Offset.Zero
            }


            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                Log.d(
                    "Fling",
                    "消费了的速度：${consumed.y},可消费的速度：${available.y}"
                )
                val delta = available.y / 10f
                val newOffset = toolbarOffsetHeightPx + delta
                val newHeight = newOffset.coerceIn(minHeightPx, maxHeightPx)
                val consumed = newHeight - toolbarOffsetHeightPx
                toolbarOffsetHeightPx = newHeight
                return Velocity(0f, consumed)
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                Log.d(
                    "Fling",
                    "初始速度：${available.y}"
                )
                return Velocity.Zero
            }
        }
    }
    Box(modifier = modifier.nestedScroll(nestedScrollConnection)) {
        val animatedHeight by animateDpAsState(
            targetValue = with(LocalDensity.current) { toolbarOffsetHeightPx.toDp() }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            state = contentScrollState,
            contentPadding = PaddingValues(top = animatedHeight)
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
        ) {
            appBar(progress)
        }
    }
}



