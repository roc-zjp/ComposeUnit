package com.zjp.compose_unit.state

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class DBState(val scaffoldState: ScaffoldState) {

    val shouldShowBottomBar: Boolean = false

    fun navigateToBottomBarRoute() {

    }

    fun showSnackbar(message: String) {
    }
}


@Composable
fun rememberDBState(scaffoldState: ScaffoldState = rememberScaffoldState()) = remember {
    DBState(scaffoldState)
}