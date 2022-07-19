package com.zjp.compose_unit.ui.developer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeveloperScreen(
    onBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "开发者页面") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = {
            TwoButtonScreen()
        }
    )
}

/**
 * 减少不必要的重构
 */
@Composable
fun DerivedState() {
    LogUtils.d("re composed")
    var counter by remember {
        mutableStateOf(0)
    }
    val counterText by derivedStateOf {
        "The counter is $counter"
    }
    Button(onClick = { counter++ }) {
        Text(text = counterText)
    }
}


@Composable
fun SnapshotFlow(scaffoldState: ScaffoldState) {

    var message by remember {
        mutableStateOf(System.currentTimeMillis())
    }
    LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
        snapshotFlow { scaffoldState.snackbarHostState }
            .mapNotNull { it.currentSnackbarData?.message }
            .distinctUntilChanged()
            .collect() { message ->
                LogUtils.d("A Snackbar with message $message was shown")
            }
    }

    var scope = rememberCoroutineScope()

    Column() {
        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("")
            }
        }) {
            Text(text = "empty message")
        }

        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message.toString())
            }
        }) {
            Text(text = "same message")
        }

        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(System.currentTimeMillis().toString())
            }
        }) {
            Text(text = "different message")
        }
    }
}

@Composable
fun LandingScreen(onTimeout: () -> Unit) {

    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(true) {
        delay(3000)
        currentOnTimeout()
    }

    /* Landing screen content */
}


@Composable
fun TwoButtonScreen() {
    var buttonColour by remember {
        mutableStateOf("Unknown")
    }
    Column {
        Button(
            onClick = {
                buttonColour = "Red"
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            )
        ) {
            Text("Red Button")
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                buttonColour = "Black"
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black
            )
        ) {
            Text("Black Button")
        }
        Timer(buttonColor = buttonColour)
    }
}

@Composable
fun Timer(
    buttonColor: String
) {
    LogUtils.d("re compose")
    val timerDuration = 5000L
    println("Composing timer with colour : $buttonColor")
    LaunchedEffect(key1 = buttonColor, block = {
        delay(timerDuration)
        println("Timer ended")
        println("Last pressed button color was $buttonColor")
    })

}