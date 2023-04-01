package com.zjp.collection.collection

import android.graphics.Region
import android.view.LayoutInflater
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import com.zjp.collection.R
import kotlin.math.roundToInt


@Composable
fun ClickableSample() {
    val count = remember { mutableStateOf(0) }
    // content that you want to make clickable
    Text(
        text = count.value.toString(),
        modifier = Modifier
            .clickable { count.value += 1 }
            .size(30.dp)
    )
}

@Composable
fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}


@Composable
fun ScrollableSample() {
    // actual composable state
    var offset by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                // Scrollable state: describes how to consume
                // scrolling delta and update offset
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(offset.toString())
    }
}

@Composable
fun AutoNestScroll() {
    val gradient = Brush.verticalGradient(0f to Color.Red, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {

            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())

                ) {
                    Text(
                        "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NestedScrollBase() {
    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
// our offset to collapse toolbar
    val toolbarOffsetHeightPx =

        remember { mutableStateOf(0f) }
// now, let's create connection to the nested scroll system and listen to the scroll
// happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)
    ) {
        // our list with build in nested scroll support that will notify us about its scroll
        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
            items(100) { index ->
                Text(
                    "I'm item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        )
    }
}


@Composable
fun DragPointInput() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

@Composable
fun DragAble() {
    var offsetX by remember { mutableStateOf(0f) }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            ),
        text = "Drag me!"
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Swipeable() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier

            // apply other transformations like rotation and zoom
            // on the pizza slice emoji
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .background(Color.Blue)
            .fillMaxSize()
    )
}

@Composable
fun NestedScrollDispatcherBase() {
    val basicState = remember { mutableStateOf(0f) }
    val minBound = -100f
    val maxBound = 100f
// lambda to update state and return amount consumed
    val onNewDelta: (Float) -> Float = { delta ->
        val oldState = basicState.value
        val newState = (basicState.value + delta).coerceIn(minBound, maxBound)
        basicState.value = newState
        newState - oldState
    }
// create a dispatcher to dispatch nested scroll events (participate like a nested scroll child)
    val nestedScrollDispatcher = remember { NestedScrollDispatcher() }

// create nested scroll connection to react to nested scroll events (participate like a parent)
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // we have no fling, so we're interested in the regular post scroll cycle
                // let's try to consume what's left if we need and return the amount consumed
                val vertical = available.y
                val weConsumed = onNewDelta(vertical)
                return Offset(x = 0f, y = weConsumed)
            }
        }
    }
    Box(
        Modifier
            .size(100.dp)
            .background(Color.LightGray)
            // attach ourselves to nested scroll system
            .nestedScroll(connection = nestedScrollConnection, dispatcher = nestedScrollDispatcher)
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    // here's regular drag. Let's be good citizens and ask parents first if they
                    // want to pre consume (it's a nested scroll contract)
                    val parentsConsumed = nestedScrollDispatcher.dispatchPreScroll(
                        available = Offset(x = 0f, y = delta),
                        source = NestedScrollSource.Drag
                    )
                    // adjust what's available to us since might have consumed smth
                    val adjustedAvailable = delta - parentsConsumed.y
                    // we consume
                    val weConsumed = onNewDelta(adjustedAvailable)
                    // dispatch as a post scroll what's left after pre-scroll and our consumption
                    val totalConsumed = Offset(x = 0f, y = weConsumed) + parentsConsumed
                    val left = adjustedAvailable - weConsumed
                    nestedScrollDispatcher.dispatchPostScroll(
                        consumed = totalConsumed,
                        available = Offset(x = 0f, y = left),
                        source = NestedScrollSource.Drag
                    )
                    // we won't dispatch pre/post fling events as we have no flinging here, but the
                    // idea is very similar:
                    // 1. dispatch pre fling, asking parents to pre consume
                    // 2. fling (while dispatching scroll events like above for any fling tick)
                    // 3. dispatch post fling, allowing parent to react to velocity left
                }
            )
    ) {
        Text(
            "State: ${basicState.value.roundToInt()}",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposeWithAndroidView() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        AndroidView({ context ->
            LayoutInflater.from(context)
                .inflate(R.layout.compose_with_androidview, null)
                .apply {
                    findViewById<ComposeView>(R.id.compose_view).apply {
                        setContent {
                            val nestedScrollInterop = rememberNestedScrollInteropConnection()
                            LazyColumn(modifier = Modifier.nestedScroll(nestedScrollInterop)) {
                                items(100) {
                                    Text(text = "item $it", modifier = Modifier.fillMaxWidth())
                                }

                            }
                        }
                    }
                }
                .also {
                    ViewCompat.setNestedScrollingEnabled(it, true)
                }
        })
    }
}

@Composable
fun RemoteControl() {

    val okColor = Color(0xFF49A0F8)

    var text by remember { mutableStateOf("") }

    val regionList = listOf(
        Region(),
        Region(),
        Region(),
        Region(),
        Region()
    )

    val path = Path()
    val okPath = Path()  // 这个路径需要用来做运算，所以单独声明一个

    Box(Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { offset: Offset ->
                            if (regionList[0].contains(offset.x.toInt(), offset.y.toInt())) {

                                text = "click OK Button"
                            }
                            if (regionList[1].contains(offset.x.toInt(), offset.y.toInt())) {

                                text = "click Button1 Button"
                            }
                            if (regionList[2].contains(offset.x.toInt(), offset.y.toInt())) {

                                text = "click Button2 Button"
                            }
                            if (regionList[3].contains(offset.x.toInt(), offset.y.toInt())) {

                                text = "click Button3 Button"
                            }
                            if (regionList[4].contains(offset.x.toInt(), offset.y.toInt())) {

                                text = "click Button4 Button"
                            }
                        }
                    )
                }
        ) {
            // 绘制外廓圆（这个园不用点击事件，只是一个线段）
            path.reset()
            path.addOval(Rect(Offset(size.width / 2, size.height / 2), 500f))
            drawPath(path, Color.White)

            // 绘制最中间的 OK 按钮
            okPath.addOval(Rect(Offset(size.width / 2, size.height / 2), 200f))
            regionList[0].setPath(
                okPath.asAndroidPath(),
                Region(0, 0, size.width.toInt(), size.height.toInt())
            )  // 这里偷懒了，直接创建了一个不满整个画布的 Region ，其实无所谓，因为是和 path 取交集，但是各位在使用的时候一定要根据自己的需求写，不要学我偷懒，不然出错了我可不负责
            drawPath(okPath, color = okColor)

            // 绘制按钮1
            path.reset()
            path.addArc(Rect(Offset(size.width / 2, size.height / 2), 500f), 225f, 90f)
            path.lineTo(size.width / 2, size.height / 2)
            path.op(path, okPath, PathOperation.Difference)
            regionList[1].setPath(
                path.asAndroidPath(),
                Region(0, 0, size.width.toInt(), size.height.toInt())
            )
            drawPath(path, color = okColor, style = Stroke(width = 2f))

            // 绘制按钮2
            path.reset()
            path.addArc(Rect(Offset(size.width / 2, size.height / 2), 500f), 315f, 90f)
            path.lineTo(size.width / 2, size.height / 2)
            path.op(path, okPath, PathOperation.Difference)
            regionList[2].setPath(
                path.asAndroidPath(),
                Region(0, 0, size.width.toInt(), size.height.toInt())
            )
            drawPath(path, color = okColor, style = Stroke(width = 2f))

            // 绘制按钮3
            path.reset()
            path.addArc(Rect(Offset(size.width / 2, size.height / 2), 500f), 45f, 90f)
            path.lineTo(size.width / 2, size.height / 2)
            path.op(path, okPath, PathOperation.Difference)
            regionList[3].setPath(
                path.asAndroidPath(),
                Region(0, 0, size.width.toInt(), size.height.toInt())
            )
            drawPath(path, color = okColor, style = Stroke(width = 2f))

            // 绘制按钮4
            path.reset()
            path.addArc(Rect(Offset(size.width / 2, size.height / 2), 500f), 135f, 90f)
            path.lineTo(size.width / 2, size.height / 2)
            path.op(path, okPath, PathOperation.Difference)
            regionList[4].setPath(
                path.asAndroidPath(),
                Region(0, 0, size.width.toInt(), size.height.toInt())
            )
            drawPath(path, color = okColor, style = Stroke(width = 2f))

            // 绘制文字
            drawIntoCanvas {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 24.sp.toPx()
                }

                // OK
                it.nativeCanvas.drawText(
                    "OK",
                    size.width / 2 - 50,
                    size.height / 2 + 25,
                    paint
                )

                paint.color = android.graphics.Color.BLACK

                // Button 1
                it.nativeCanvas.drawText(
                    "Button1",
                    size.width / 2 - 110,
                    size.height / 2 - 250,
                    paint
                )

                // Button 2
                it.nativeCanvas.drawText(
                    "Button2",
                    size.width / 2 + 250,
                    size.height / 2 + 20,
                    paint
                )

                // Button 3
                it.nativeCanvas.drawText(
                    "Button3",
                    size.width / 2 - 110,
                    size.height / 2 + 300,
                    paint
                )

                // Button 4
                it.nativeCanvas.drawText(
                    "Button4",
                    size.width / 2 - 450,
                    size.height / 2 + 20,
                    paint
                )
            }

        }

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text, fontSize = 22.sp, color = Color.White)
        }
    }

}