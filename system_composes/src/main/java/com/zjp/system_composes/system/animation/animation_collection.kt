package com.zjp.system_composes.system.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.zjp.common.compose.WrapLayout
import com.zjp.system_composes.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * 【enter】：进场动画【EnterTransition】
 * 【exit】：退场动画【ExitTransition】
 *
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityBase() {
    var visible by remember {
        mutableStateOf(true)
    }

    var isSpring by remember {
        mutableStateOf(false)
    }

    var enterAnimaList = listOf<Pair<String, EnterTransition>>(
        Pair("fadeIn", fadeIn(sp(isSpring))),
        Pair(
            "slideInHorizontally",
            slideInHorizontally(animationSpec = tween(durationMillis = 5000))
        ),
        Pair("slideInVertically", slideInVertically(tween(durationMillis = 5000))),
        Pair("scaleIn", scaleIn(tween(durationMillis = 5000))),
        Pair("expandIn", expandIn(tween(durationMillis = 5000))),
        Pair("expandHorizontally", expandHorizontally(tween(durationMillis = 5000))),
        Pair("expandVertically", expandVertically(tween(durationMillis = 5000))),

        )
    var outAnimaList = listOf<Pair<String, ExitTransition>>(
        Pair("fadeOut", fadeOut(animationSpec = tween(durationMillis = 5000))),
        Pair(
            "slideOutHorizontally",
            slideOutHorizontally(animationSpec = tween(durationMillis = 5000))
        ),
        Pair("slideOutVertically", slideOutVertically(tween(durationMillis = 5000))),
        Pair("scaleOut", scaleOut(tween(durationMillis = 5000))),
        Pair("shrinkOut", shrinkOut(tween(durationMillis = 5000))),
        Pair("shrinkHorizontally", shrinkHorizontally(tween(durationMillis = 5000))),
        Pair("shrinkVertically", shrinkVertically(tween(durationMillis = 5000)))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Switch(
            checked = visible, onCheckedChange = { value ->
                visible = value
            },
            Modifier
                .height(20.dp)
                .align(Alignment.TopCenter)
        )
        WrapLayout(modifier = Modifier.padding(top = 20.dp)) {
            enterAnimaList.forEachIndexed { index, pair ->
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth(0.5f),
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = visible,
                        enter = pair.second,
                        exit = outAnimaList[index].second,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .align(Alignment.TopCenter),
                        content = {
                            Image(
                                painter = painterResource(id = com.zjp.common.R.drawable.jetpack_compose),
                                contentDescription = "icon",
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    )
                    Text(
                        text = "enter:${pair.first}\nexit:${outAnimaList[index].first}",
                        Modifier
                            .padding(top = 100.dp)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

fun <T> sp(b: Boolean): FiniteAnimationSpec<T> {
    return if (b) {
        tween(durationMillis = 5000)
    } else {
        spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilitySpring() {
    var visible by remember {
        mutableStateOf(true)
    }
    var sp = spring<Number>(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessVeryLow
    )

    var enterAnimaList = listOf<Pair<String, EnterTransition>>(
        Pair(
            "fadeIn", fadeIn(sp as SpringSpec<Float>)
        ),
        Pair(
            "slideInHorizontally",
            slideInHorizontally(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "slideInVertically", slideInVertically(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "scaleIn", scaleIn(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "expandIn", expandIn(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "expandHorizontally", expandHorizontally(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "expandVertically", expandVertically(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),

        )
    var outAnimaList = listOf<Pair<String, ExitTransition>>(
        Pair(
            "fadeOut", fadeOut(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "slideOutHorizontally",
            slideOutHorizontally(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "slideOutVertically", slideOutVertically(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "scaleOut", scaleOut(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "shrinkOut", shrinkOut(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "shrinkHorizontally", shrinkHorizontally(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ),
        Pair(
            "shrinkVertically", shrinkVertically(
                spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        )
    )

    var dampingRatio by remember {
        mutableStateOf(0.01f)
    }
    var stiffness by remember {
        mutableStateOf(0.01f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Switch(
            checked = visible, onCheckedChange = { value ->
                visible = value
            },
            Modifier
                .height(20.dp)
                .align(Alignment.TopCenter)
        )
        Row {
            Text(text = "dampingRatio")
            Slider(value = dampingRatio, onValueChange = { value ->
                dampingRatio = value
            })
        }
        Row {
            Text(text = "stiffness")
            Slider(value = stiffness, onValueChange = { value ->
                stiffness = value
            })
        }

        WrapLayout(modifier = Modifier.padding(top = 20.dp)) {
            enterAnimaList.forEachIndexed { index, pair ->
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth(0.5f),
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = visible,
                        enter = pair.second,
                        exit = outAnimaList[index].second,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .align(Alignment.TopCenter),
                        content = {
                            Image(
                                painter = painterResource(id = com.zjp.common.R.drawable.jetpack_compose),
                                contentDescription = "icon",
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    )
                    Text(
                        text = "enter:${pair.first}\nexit:${outAnimaList[index].first}",
                        Modifier
                            .padding(top = 100.dp)
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

@Composable
fun <T> SpringGroup(value: T, items: List<T>, onItemSelect: (T) -> Unit) {
    Row(
        Modifier
            .selectableGroup()
            .horizontalScroll(rememberScrollState())
    ) {
        items.forEach { item ->
            RadioButton(selected = value == item, onClick = { onItemSelect(item) })
        }
    }
}

/**
 * 添加到了组合树立即触发
 */
@Composable
fun AnimatedMutableTransitionState() {
    var state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(animationSpec = tween(durationMillis = 10000))
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .background(Color.Red)
            )
        }
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                state.isIdle && !state.currentState -> "Invisible"
                else -> "Appearing"
            }
        )

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChildAnimated() {
    var visible by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 5000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 5000))
    ) {
        // Fade in/out the background and the foreground.
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .animateEnterExit(
                        // Slide in/out the inner box.
                        enter = slideInVertically(animationSpec = tween(durationMillis = 5000)),
                        exit = slideOutVertically(animationSpec = tween(durationMillis = 5000))
                    )
                    .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                    .background(Color.Red)
            ) {
                // Content of the notification…
            }
        }
    }
    Button(onClick = {
        visible = !visible
    }) {
        Text(text = "Toggle")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedTransition() {
    var visible by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(durationMillis = 5000)),
        exit = fadeOut(tween(durationMillis = 5000))
    ) { // this: AnimatedVisibilityScope
        // Use AnimatedVisibilityScope#transition to add a custom animation
        // to the AnimatedVisibility.
        val background by transition.animateColor(label = "") { state ->
            if (state == EnterExitState.Visible) Color.Blue else Color.Gray
        }
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(background)
        )
    }
    Button(onClick = {
        visible = !visible
    }) {
        Text(text = "Toggle")
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentWithContentTransform() {
    var state by remember {
        mutableStateOf(0)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                // Compare the incoming number with the previous number.
                if (targetState > initialState) {
                    // If the target number is larger, it slides up and fades in
                    // while the initial (smaller) number slides up and fades out.
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    // If the target number is smaller, it slides down and fades in
                    // while the initial number slides down and fades out.
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            Text(text = "$targetCount")
        }
        Row {
            Button(onClick = {
                state--
            }) {
                Text(text = "-")
            }
            Button(onClick = { state++ }) {
                Text(text = "+")
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentBase() {
    Column {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        AnimatedContent(targetState = count) { targetCount ->
            // Make sure to use `targetCount`, not `count`.
            Text(text = "Count: $targetCount")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentSizeTransform() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        onClick = { expanded = !expanded }
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Expanded()
            } else {
                ContentIcon()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentChild() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        onClick = { expanded = !expanded }
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->

            val background by transition.animateColor(label = "") { state ->
                if (state == EnterExitState.Visible) Color.Blue else Color.Gray
            }
            if (targetExpanded) {
                Expanded(
                    Modifier
                        .animateEnterExit(
                            enter = fadeIn(tween(durationMillis = 5000)),
                            exit = fadeOut(tween(durationMillis = 5000))
                        )
                        .background(background)
                )
            } else {
                ContentIcon(
                    Modifier
                        .animateEnterExit(
                            enter = slideInVertically(tween(durationMillis = 5000)),
                            exit = slideOutVertically(tween(durationMillis = 5000))
                        )
                        .background(background)
                )
            }
        }
    }
}


@Composable
fun Expanded(modifier: Modifier = Modifier) {
    Text(
        text = "dadaskkmkskdsadsadsadsadsadsadksdksdjksfdjfnd\ndsadsadsadsadsada\ndsadasdasdsadasdsaddsdasadsa\ndsdasdsaklkkd;kMKDmasdmamdsamdklsnfdsnfnlsdfnsfns\nadksadaskdkakldskadksa\n",
        modifier
    )
}

@Composable
fun ContentIcon(modifier: Modifier = Modifier) {
    Icon(Icons.Default.Phone, contentDescription = "phone", modifier)
}

@Composable
fun ModifierContentAnimated() {
    var message by remember { mutableStateOf("Hello") }
    Box(
        modifier = Modifier
            .background(Color.Green)
            .animateContentSize(animationSpec = tween(durationMillis = 5000))
            .clickable {
                message = if (message == "Hello") {
                    System
                        .currentTimeMillis()
                        .toString()
                } else {
                    "Hello"
                }
            }
    ) {
        Text(text = message)
    }

}

@Composable
fun CrossfadeBase() {
    var currentPage by remember { mutableStateOf("A") }
    Crossfade(targetState = currentPage,
        animationSpec = tween(5000),
        modifier = Modifier.clickable {
            currentPage = if (currentPage == "A") {
                "B"
            } else {
                "A"
            }
        }) { screen ->
        when (screen) {
            "A" -> Text("Page A Click Me", Modifier.background(Color.Red))
            "B" -> Text("Page B Click Me", Modifier.background(Color.Green))
        }
    }
}

@Composable
fun Gesture() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        val position = awaitPointerEventScope {
                            awaitFirstDown().position
                        }
                        launch {
                            // Animate to the tap position.
                            offset.animateTo(position)
                        }
                    }
                }
            }
    ) {
        Circle(
            radius = 50.dp,
            modifier = Modifier.offset { offset.value.toIntOffset(50.dp.roundToPx()) })
    }
}

@Composable
fun Circle(modifier: Modifier = Modifier, radius: Dp) {
    Image(
        painter = painterResource(id = com.zjp.common.R.drawable.jetpack_compose),
        contentDescription = "circe",
        modifier
            .width(100.dp)
            .height(100.dp)
            .clip(
                CircleShape
            )
    )
}


private fun Offset.toIntOffset(radius: Int) =
    IntOffset(
        x.roundToInt() - radius,
        y.roundToInt() - radius
    )


@Preview
@Composable
fun AnimatedPreview() {
    AnimatedTransition()
}