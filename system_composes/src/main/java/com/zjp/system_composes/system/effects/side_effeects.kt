package com.zjp.system_composes.system.effects

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*

@Composable
fun LaunchedEffectWithSuspend() {

    val hasError by remember {
        mutableStateOf(false)
    }
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    if (hasError) {
        LaunchedEffect(scaffoldState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message",
                actionLabel = "Retry message"
            )
        }
    }

    Scaffold(scaffoldState = scaffoldState) {

    }
}