package com.zjp.collection.collection.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedVisibilityDemo() {
    var visible by remember { mutableStateOf(true) }
    val density = LocalDensity.current


    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = !visible }) {
            Text(text = "切换")
        }
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(
                "Hello",
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    }
}

@Composable
fun startAnimationImmediately() {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }
    Column {
        AnimatedVisibility(visibleState = state) {
            Text(text = "Hello, world!")
        }

        // Use the MutableTransitionState to know the current animation state
        // of the AnimatedVisibility.
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
fun ExitAndInAnimation() {
    var visible by remember { mutableStateOf(true) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = !visible }) {
            Text(text = "切换")
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
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
                            enter = slideInVertically(),
                            exit = slideOutVertically()
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    // Content of the notification…
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun customAnimation() {
    var visible by remember { mutableStateOf(true) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { visible = !visible }) {
            Text(text = "切换")
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
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
    }
}


@Composable
fun animateAsState() {

    var enabled by remember { mutableStateOf(true) }
    val alpha: Float by animateFloatAsState(if (enabled) 1f else 0.5f)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = { enabled = !enabled }) {
            Text(text = "切换")
        }
        Box(
            Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = alpha)
                .background(Color.Red)
        )
    }
}