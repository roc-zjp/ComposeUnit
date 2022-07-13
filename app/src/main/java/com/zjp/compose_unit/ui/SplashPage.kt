package com.zjp.compose_unit.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.R
import com.zjp.core_database.DBManager
import kotlinx.coroutines.delay

@Composable
fun SplashView(toHomePage: () -> Unit) {
    val context = LocalContext.current
    var state by remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        state = true
        DBManager.initDB(context.applicationContext)
        delay(3000)
        toHomePage()
    }

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    visible = state,
                    enter = slideInVertically(
                        initialOffsetY = { -1000 },
                        animationSpec = tween(durationMillis = 1200)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 1200)
                    )
                ) {
                    Image(
                        painter = painterResource(id = com.zjp.system_composes.R.drawable.jetpack_compose),
                        contentDescription = "Compose",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                AnimatedVisibility(
                    visible = state,
                    enter = slideInVertically(
                        initialOffsetY = { 1000 },
                        animationSpec = tween(durationMillis = 1200)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 1200)
                    )
                ) {
                    Text(text = "Compose Unit", fontSize = 24.sp)
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPre() {
    SplashView() {

    }
}