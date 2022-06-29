package com.zjp.compose_unit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    LaunchedEffect(true) {
        DBManager.initDB(context.applicationContext)
        delay(3000)
        toHomePage()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painter = painterResource(id = R.drawable.jetpack_compose),
                contentDescription = "Compose",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(100.dp)
                    .height(100.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Compose Unit", fontSize = 24.sp)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun defaultPre() {
    SplashView() {

    }
}