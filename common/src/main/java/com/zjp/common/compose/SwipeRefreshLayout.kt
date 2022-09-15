package com.zjp.common.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PullRefreshLoadMoreLayout(
    pullRefreshAble: Boolean = true,
    loadMoreAble: Boolean = true,
    refreshTriggerDp: Dp = 120.dp,
    loadTriggerDp: Dp = 120.dp,
    loading: Boolean = false,
    refresh: suspend () -> Unit = {},
    loadMore: suspend () -> Unit = {},
    modifier: Modifier = Modifier,
    state: PullRefreshState = rememberPullState(
        pullRefreshAble,
        loadMoreAble,
        loading,
        refreshTriggerDp,
        loadTriggerDp,
        coroutineScope = rememberCoroutineScope(),
        refresh,
        loadMore
    ),
    header: @Composable (state: PullRefreshState) -> Unit = {
        defaultHeader(state = it)
    },
    bottom: @Composable (state: PullRefreshState) -> Unit = {
        defaultBottom(state = it)
    },
    content: @Composable (contentHeaderPadding: Dp, contentBottomPadding: Dp) -> Unit
) {
    val scope = rememberCoroutineScope()
    val nestedScrollConnection = remember {
        PullRefreshNestedScrollConnection(state, scope)
    }
    LaunchedEffect(key1 = state.isRefreshing) {
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
            with(LocalDensity.current) { state.headerOffset.toDp() + 0.5.dp },
            with(LocalDensity.current) { state.bottomOffset.toDp() + 0.5.dp })
        header(state)
        Box(modifier = Modifier
            .align(Alignment.BottomStart)
            .clipToBounds()) {
            bottom(state)
        }
    }
}

@Composable
fun rememberPullState(
    pullRefreshAble: Boolean,
    loadMoreAble: Boolean,
    loading: Boolean = false,
    refreshTriggerDp: Dp = 80.dp,
    loadTriggerDp: Dp = 80.dp,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    refresh: suspend () -> Unit = {},
    loadMore: suspend () -> Unit = {},
): PullRefreshState {

    val density = LocalDensity.current
    return remember {
        PullRefreshState(
            pullRefreshAble,
            loadMoreAble,
            loading,
            with(density) { refreshTriggerDp.toPx() },
            with(density) { loadTriggerDp.toPx() },
            coroutineScope,
            refresh,
            loadMore,
        )
    }.apply { this.isRefreshing = loading }
}


internal class PullRefreshNestedScrollConnection(
    val state: PullRefreshState,
    private val coroutineScope: CoroutineScope
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
        coroutineScope.launch { state.dispatchPostFling(0f) }
        return Velocity.Zero
    }
}


class PullRefreshState(
    private val pullRefreshAble: Boolean,
    private val loadMoreAble: Boolean,
    loading: Boolean,
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
            LogUtils.d("下滑:delta=$delta,consumed=$consumed")
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

    internal suspend fun dispatchPostFling(delta: Float) {
        if (headerOffset > refreshTriggerPx) {
            isRefreshing = true
            _headerOffset.animateTo(refreshTriggerPx)
            refresh()
        } else {
            _headerOffset.animateTo(0f)
        }
        if (bottomOffset > loadTriggerPx) {
            _bottomOffset.animateTo(loadTriggerPx)
            isRefreshing = true
            loadMore()
        } else {
            _bottomOffset.animateTo(0f)
        }
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
fun defaultHeader(state: PullRefreshState) {
    Box(
        modifier = Modifier
            .height(with(LocalDensity.current) { state.headerOffset.toDp() })
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

            if (state.isRefreshing) {
                CircularProgressIndicator(
                    color = Color.Red,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                )
            } else {
                CircularProgressIndicator(
                    progress = state.calculateHeadProgress(),
                    color = Color.Red,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                )
            }

            Text(text = if (state.isRefreshing) "正在刷新" else "下拉刷新")
        }
    }
}

@Composable
fun defaultBottom(state: PullRefreshState) {
    Box(
        modifier = Modifier
            .height(with(LocalDensity.current) { state.bottomOffset.toDp() })
            .background(Color.Gray)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.isRefreshing) {
                CircularProgressIndicator(
                    color = Color.Red,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                )
            } else {
                CircularProgressIndicator(
                    progress = state.calculateBottomProgress(),
                    color = Color.Red,
                    strokeWidth = 10.dp,
                    modifier = Modifier
                )
            }
            Text(text = if (state.isRefreshing) "正在刷新" else "上拉加载更多")
        }
    }
}

