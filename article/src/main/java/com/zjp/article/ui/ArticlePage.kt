package com.zjp.article.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apkfuns.logutils.LogUtils


@Composable
fun ArticlePage() {

    FoldAppbar(
        minHeightDp = 50.dp,
        appbar = {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxSize()
            )
        },
        content = { height ->
            LazyColumn(contentPadding = PaddingValues(top = height)) {
                items(100) { index ->
                    Text(text = "item $index", modifier = Modifier.fillMaxWidth())
                }
            }
        })
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
                LogUtils.d("scrollHeight=$scrollHeight,delta=$delta")
                scrollHeight += delta
                val newHeight = with(density) { (maxHeight + scrollHeight).toDp() }
                topHeight = newHeight.coerceIn(minHeightDp, maxHeightDp)
                progress = (maxHeightDp - topHeight) / maxHeightDp
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
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    BoxWithConstraints(
        modifier = Modifier.heightIn()
    ) {

        content()
    }


    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->


        val appbarMinheight = constraints.minHeight
        val appbarMaxheight = constraints.maxHeight
        LogUtils.d("max min,$appbarMaxheight,$appbarMinheight")
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {

            placeables.forEach {
                it.placeRelative(0, 0)
            }
        }
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

