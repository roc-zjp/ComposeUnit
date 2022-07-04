package com.zjp.system_composes.widgets


import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * 【progress】：进度，为空时，一直循环加载
 * 【backgroundColor】：背景颜色
 * 【color】: 前景颜色
 */
@Composable
fun LinearProgress() {
    LinearProgressIndicator(
        progress = 0.7f,
        backgroundColor = Color.LightGray,
        color = Color.Red,
    )
}

/**
 * 【progress】：进度，为空时，一直转圈圈
 * 【color】: 前景颜色
 * 【strokeWidth】：线条宽度
 */
@Composable
fun CircularProgress() {
    CircularProgressIndicator(
        progress = 0.5f,
        color = Color.Red,
        strokeWidth = 10.dp
    )
}

@Composable
private fun CircularProgressAnimated() {
    val progressValue = 0.75f
    var progress by remember { mutableStateOf(0.1f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(30.dp))
        Text("CircularProgressIndicator with undefined progress")
        CircularProgressIndicator()
        Spacer(Modifier.height(30.dp))
        Text("CircularProgressIndicator with progress set by buttons")
        CircularProgressIndicator(progress = animatedProgress, color = Color.Red)
        Spacer(Modifier.height(30.dp))
        OutlinedButton(
            onClick = {
                if (progress < 1f) progress += 0.1f
            }
        ) {
            Text("Increase")
        }

        OutlinedButton(
            onClick = {
                if (progress > 0f) progress -= 0.1f
            }
        ) {
            Text("Decrease")
        }
    }
}

@Preview("Home list drawer screen")
@Composable
fun ProgressCollection() {
    Column {
        LinearProgress()
        LinearProgressIndicator()
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgress()
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressAnimated()

    }
}