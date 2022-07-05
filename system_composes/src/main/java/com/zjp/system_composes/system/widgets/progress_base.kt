package com.zjp.system_composes.system.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ProgressCollection() {
    Column {

        Spacer(modifier = Modifier.height(10.dp))
        CircularProgress()
        Spacer(modifier = Modifier.height(10.dp))
        CircularProgressAnimated()

    }
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

/**
 * 【progress】：进度，为空时，一直循环加载
 * 【backgroundColor】：背景颜色
 * 【color】: 前景颜色
 */
@Composable
fun LinearProgress() {
    Row {
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressIndicator(
            progress = 0.7f,
            backgroundColor = Color.LightGray,
            color = Color.Red,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressIndicator(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(10.dp))
    }
}

/**
 * 【progress】：进度，为空时，一直转圈圈【Float】
 * 【color】: 前景颜色【color】
 * 【strokeWidth】：线条宽度【DP]
 */
@Composable
fun CircularProgress() {
    Row {
        CircularProgressIndicator(
            progress = 0.5f,
            color = Color.Red,
            strokeWidth = 10.dp
        )
        Spacer(modifier = Modifier.width(10.dp))
        CircularProgressIndicator()
    }
}
