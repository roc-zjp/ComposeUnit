package com.zjp.common.compose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.pow

@Composable
fun RefreshLayout(
    refreshEnable: Boolean = true,
    loadEnable: Boolean = true,
    refreshTriggerDp: Dp = 120.dp,
    loadTriggerDp: Dp = 120.dp,
    loading: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    state: PullRefreshState = rememberRefreshState(
        refreshEnable,
        loadEnable,
        loading,
        refreshTriggerDp,
        loadTriggerDp,
        rememberCoroutineScope(),
        onRefresh,
        onLoadMore
    ),
    modifier: Modifier = Modifier,
    header: @Composable (isRefreshing: Boolean, headerOffset: Float, progress: Float) -> Unit = { isRefreshing, headerOffset, progress ->
//        DefaultHeader(isRefreshing, headerOffset, progress)
        SwipeRefreshIndicator(
            refreshTriggerDistance = refreshTriggerDp,
            progress = progress,
            isRefreshing = isRefreshing,
            offsetY = headerOffset
        )
    },
    bottom: @Composable (isRefreshing: Boolean, bottomOffset: Float, progress: Float) -> Unit = { isRefreshing, bottomOffset, progress ->
//        DefaultBottom(isRefreshing, bottomOffset, progress)
        SwipeRefreshIndicator(
            refreshTriggerDistance = refreshTriggerDp,
            progress = progress,
            isRefreshing = isRefreshing,
            offsetY = bottomOffset
        )
    },

    content: @Composable (contentHeaderPadding: Dp, contentBottomPadding: Dp) -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current


    val nestedScrollConnection = remember {
        RefreshNestedScrollConnection(state)
    }

    LaunchedEffect(state.isRefreshing) {
        if (!state.isRefreshing) {
            state.reset()
        }
    }
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        content(
            with(density) { state.headerOffset.toDp() + 0.5.dp },
            with(density) { state.bottomOffset.toDp() + 0.5.dp })
        header(state.isRefreshing, state.headerOffset, state.calculateHeadProgress())
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .clipToBounds()
        ) {
            bottom(state.isRefreshing, state.bottomOffset, state.calculateBottomProgress())
        }
    }
}

@Composable
fun rememberRefreshState(
    pullRefreshAble: Boolean,
    loadMoreAble: Boolean,
    loading: Boolean = false,
    refreshTriggerDp: Dp = 80.dp,
    loadTriggerDp: Dp = 80.dp,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
): PullRefreshState {
    val density = LocalDensity.current
    val updatedOnRefresh = rememberUpdatedState(onRefresh)
    val updatedOnLoad = rememberUpdatedState(onLoadMore)

    return remember() {
        PullRefreshState(
            pullRefreshAble,
            loadMoreAble,
            loading,
            with(density) { refreshTriggerDp.toPx() },
            with(density) { loadTriggerDp.toPx() },
            coroutineScope,
            updatedOnRefresh.value,
            updatedOnLoad.value,
        )
    }.apply {
        this.isRefreshing = loading
        this.pullRefreshAble = pullRefreshAble
        this.loadMoreAble = loadMoreAble
    }
}

internal class RefreshNestedScrollConnection(
    val state: PullRefreshState,
) : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        return state.dispatchPreScroll(available.y)
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        return state.dispatchPostScroll(available.y)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return state.dispatchPostFling(0f)
    }
}


class PullRefreshState(
    var pullRefreshAble: Boolean,
    var loadMoreAble: Boolean,
    var loading: Boolean,
    private val refreshTriggerPx: Float,
    private val loadTriggerPx: Float,
    private val coroutineScope: CoroutineScope,
    val refresh: suspend () -> Unit = {},
    val loadMore: suspend () -> Unit = {},
) {

    private val _headerOffset = Animatable(0f)
    private val _bottomOffset = Animatable(0f)

    var isRefreshing: Boolean by mutableStateOf(loading)

    val headerOffset: Float get() = _headerOffset.value

    val bottomOffset: Float get() = _bottomOffset.value

    internal fun dispatchPreScroll(delta: Float): Offset {
        if (isRefreshing) {
            return Offset(x = 0f, y = delta)
        }
        if (delta > 0 && bottomOffset != 0f) {//下滑,考虑是否恢复加载更多
            val newHeight = (bottomOffset - delta).coerceAtLeast(0f)
            val consumed = bottomOffset - newHeight
            coroutineScope.launch {
                _bottomOffset.snapTo(newHeight + delta / 2)
            }
            return Offset(x = 0f, y = consumed)
        }


        if (delta < 0 && headerOffset != 0f) {//上滑,考虑是否恢复刷新
            val newHeight = (headerOffset + delta).coerceAtLeast(0f)
            val consumed = newHeight - headerOffset
            coroutineScope.launch { _headerOffset.snapTo(newHeight - delta / 2) }
            return Offset(0f, consumed)
        }
        return Offset.Zero
    }

    internal fun dispatchPostScroll(delta: Float): Offset {
        if (delta < 0 && loadMoreAble) {
            coroutineScope.launch {
                _bottomOffset.snapTo(bottomOffset - delta / 2)
            }
        }

        if (delta > 0 && pullRefreshAble) {
            coroutineScope.launch {
                _headerOffset.snapTo(headerOffset + delta / 2)
            }
        }
        return Offset(0f, delta)
    }

    internal fun dispatchPostFling(delta: Float): Velocity {
        coroutineScope.launch {
            if (headerOffset > refreshTriggerPx) {

                _headerOffset.animateTo(refreshTriggerPx)
                refresh()
            } else {
                _headerOffset.animateTo(0f)
            }
            if (bottomOffset > loadTriggerPx) {
                _bottomOffset.animateTo(loadTriggerPx)

                loadMore()
            } else {
                _bottomOffset.animateTo(0f)
            }
        }
        return Velocity.Zero
    }

    fun calculateHeadProgress(): Float {
        return (headerOffset / refreshTriggerPx).coerceIn(0f, 1f)
    }

    fun calculateBottomProgress(): Float {
        return (bottomOffset / loadTriggerPx).coerceIn(0f, 1f)
    }

    suspend fun reset() {
        if (headerOffset != 0f) {
            _headerOffset.animateTo(0f)
        }
        if (bottomOffset != 0f) {
            _bottomOffset.animateTo(0f)
        }
    }

}


@Composable
fun DefaultHeader(isRefreshing: Boolean, headerOffset: Float, progress: Float) {
    Box(
        modifier = Modifier
            .height(with(LocalDensity.current) { headerOffset.toDp() })
            .fillMaxWidth()
            .background(Color.Gray)
            .clipToBounds()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isRefreshing) {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier
                )
            } else {
                CircularProgressIndicator(
                    progress = progress,
                    color = Color.Red,
                    modifier = Modifier
                )
            }

            Text(text = if (isRefreshing) "正在刷新" else "下拉刷新")
        }
    }
}

@Composable
fun DefaultBottom(isRefreshing: Boolean, bottomOffset: Float, progress: Float) {
    Box(
        modifier = Modifier
            .height(with(LocalDensity.current) { bottomOffset.toDp() })
            .background(Color.Gray)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isRefreshing) {
                CircularProgressIndicator(
                    color = Color.Red,
                    modifier = Modifier
                )
            } else {
                CircularProgressIndicator(
                    progress = progress,
                    color = Color.Red,
                    modifier = Modifier
                )
            }
            Text(text = if (isRefreshing) "正在刷新" else "上拉加载更多")
        }
    }
}


@Composable
fun SwipeRefreshIndicator(
    offsetY: Float,
    progress: Float,
    isRefreshing: Boolean,
    refreshTriggerDistance: Dp,
    modifier: Modifier = Modifier,
    arrowEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),

    elevation: Dp = 6.dp,
) {
    val sizes = DefaultSizes

    val indicatorRefreshTrigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    val indicatorHeight = with(LocalDensity.current) { sizes.size.roundToPx() }

    val slingshot = rememberUpdatedSlingshot(
        offsetY = offsetY,
        maxOffsetY = indicatorRefreshTrigger,
        height = indicatorHeight,
    )

    var offset by remember { mutableStateOf(0f) }

    val adjustedElevation = when {
        isRefreshing -> elevation
        offset > 0.5f -> elevation
        else -> 0.dp
    }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .height(with(density) { offsetY.toDp() })
            .fillMaxWidth()
            .clipToBounds()
    ) {
        Surface(
            modifier = modifier
                .size(size = sizes.size)
                .align(Alignment.Center),
            shape = shape,
            color = backgroundColor,

            ) {
            val painter = remember { CircularProgressPainter() }
            painter.arcRadius = sizes.arcRadius
            painter.strokeWidth = sizes.strokeWidth
            painter.arrowWidth = sizes.arrowWidth
            painter.arrowHeight = sizes.arrowHeight
            painter.arrowEnabled = arrowEnabled && !isRefreshing
            painter.color = contentColor
            val alpha = progress
            painter.alpha = alpha
            painter.startTrim = slingshot.startTrim
            painter.endTrim = slingshot.endTrim
            painter.rotation = slingshot.rotation
            painter.arrowScale = slingshot.arrowScale

            // This shows either an Image with CircularProgressPainter or a CircularProgressIndicator,
            // depending on refresh state
            Crossfade(
                targetState = isRefreshing,
                animationSpec = tween(durationMillis = CrossfadeDurationMs), label = ""
            ) { refreshing ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (refreshing) {
                        val circleSize = (sizes.arcRadius + sizes.strokeWidth) * 2
                        CircularProgressIndicator(
                            color = contentColor,
                            strokeWidth = sizes.strokeWidth,
                            modifier = Modifier.size(circleSize),
                        )
                    } else {
                        Image(
                            painter = painter,
                            contentDescription = "Refreshing"
                        )
                    }
                }
            }
        }
    }
}

private val DefaultSizes = SwipeRefreshIndicatorSizes(
    size = 40.dp,
    arcRadius = 7.5.dp,
    strokeWidth = 2.5.dp,
    arrowWidth = 10.dp,
    arrowHeight = 5.dp,
)

internal class CircularProgressPainter : Painter() {
    var color by mutableStateOf(Color.Unspecified)
    var alpha by mutableStateOf(1f)
    var arcRadius by mutableStateOf(0.dp)
    var strokeWidth by mutableStateOf(5.dp)
    var arrowEnabled by mutableStateOf(false)
    var arrowWidth by mutableStateOf(0.dp)
    var arrowHeight by mutableStateOf(0.dp)
    var arrowScale by mutableStateOf(1f)

    private val arrow: Path by lazy {
        Path().apply { fillType = PathFillType.EvenOdd }
    }

    var startTrim by mutableStateOf(0f)
    var endTrim by mutableStateOf(0f)
    var rotation by mutableStateOf(0f)

    override val intrinsicSize: Size
        get() = Size.Unspecified

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun DrawScope.onDraw() {
        rotate(degrees = rotation) {
            val arcRadius = arcRadius.toPx() + strokeWidth.toPx() / 2f
            val arcBounds = Rect(
                size.center.x - arcRadius,
                size.center.y - arcRadius,
                size.center.x + arcRadius,
                size.center.y + arcRadius
            )
            val startAngle = (startTrim + rotation) * 360
            val endAngle = (endTrim + rotation) * 360
            val sweepAngle = endAngle - startAngle
            drawArc(
                color = color,
                alpha = alpha,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = arcBounds.topLeft,
                size = arcBounds.size,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Square
                )
            )
            if (arrowEnabled) {
                drawArrow(startAngle, sweepAngle, arcBounds)
            }
        }
    }

    private fun DrawScope.drawArrow(startAngle: Float, sweepAngle: Float, bounds: Rect) {
        arrow.reset()
        arrow.moveTo(0f, 0f)
        arrow.lineTo(
            x = arrowWidth.toPx() * arrowScale,
            y = 0f
        )
        arrow.lineTo(
            x = arrowWidth.toPx() * arrowScale / 2,
            y = arrowHeight.toPx() * arrowScale
        )
        val radius = min(bounds.width, bounds.height) / 2f
        val inset = arrowWidth.toPx() * arrowScale / 2f
        arrow.translate(
            Offset(
                x = radius + bounds.center.x - inset,
                y = bounds.center.y + strokeWidth.toPx() / 2f
            )
        )
        arrow.close()
        rotate(degrees = startAngle + sweepAngle) {
            drawPath(
                path = arrow,
                color = color,
                alpha = alpha
            )
        }
    }
}

@Composable
internal fun rememberUpdatedSlingshot(
    offsetY: Float,
    maxOffsetY: Float,
    height: Int
): Slingshot {
    val offsetPercent = min(1f, offsetY / maxOffsetY)
    val adjustedPercent = kotlin.math.max(offsetPercent - 0.4f, 0f) * 5 / 3
    val extraOffset = abs(offsetY) - maxOffsetY

    // Can accommodate custom start and slingshot distance here
    val slingshotDistance = maxOffsetY
    val tensionSlingshotPercent = kotlin.math.max(
        0f, min(extraOffset, slingshotDistance * 2) / slingshotDistance
    )
    val tensionPercent = (
            (tensionSlingshotPercent / 4) -
                    (tensionSlingshotPercent / 4).pow(2)
            ) * 2
    val extraMove = slingshotDistance * tensionPercent * 2
    val targetY = height + ((slingshotDistance * offsetPercent) + extraMove).toInt()
    val offset = targetY - height
    val strokeStart = adjustedPercent * 0.8f

    val startTrim = 0f
    val endTrim = strokeStart.coerceAtMost(MaxProgressArc)

    val rotation = (-0.25f + 0.4f * adjustedPercent + tensionPercent * 2) * 0.5f
    val arrowScale = min(1f, adjustedPercent)

    return remember { Slingshot() }.apply {
        this.offset = offset
        this.startTrim = startTrim
        this.endTrim = endTrim
        this.rotation = rotation
        this.arrowScale = arrowScale
    }
}

@Stable
internal class Slingshot {
    var offset: Int by mutableStateOf(0)
    var startTrim: Float by mutableStateOf(0f)
    var endTrim: Float by mutableStateOf(0f)
    var rotation: Float by mutableStateOf(0f)
    var arrowScale: Float by mutableStateOf(0f)
}

internal const val MaxProgressArc = 0.8f


private const val CrossfadeDurationMs = 100

@Immutable
private data class SwipeRefreshIndicatorSizes(
    val size: Dp,
    val arcRadius: Dp,
    val strokeWidth: Dp,
    val arrowWidth: Dp,
    val arrowHeight: Dp,
)