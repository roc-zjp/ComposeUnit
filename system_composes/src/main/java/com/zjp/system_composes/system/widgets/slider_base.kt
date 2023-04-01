package com.zjp.system_composes.system.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.util.toRange

/**
 *【value】：当前值【Float】
 *【onValueChange】：进度变化时的回调【Function】
 *【color】：颜色【Color】
 */
@Composable
fun SliderBase() {
    var progress by remember {
        mutableStateOf(0f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "当前值：$progress")
        Slider(
            value = progress,
            onValueChange = { value ->
                progress = value
            },
            colors = SliderDefaults.colors()
        )
    }
}


/**
 * 【steps】：步数【Int】
 * 【valueRange】：最大最小范围【ClosedFloatingPointRange】
 */
@Composable
fun SliderBaseWithStep() {
    var progress by remember {
        mutableStateOf(0f)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "当前值：$progress")
        Slider(
            value = progress,
            onValueChange = { value ->
                progress = value
            },
            steps = 9,
            valueRange = 0.0f..100f
        )
    }
}

/**
 *【values】：当前范围【ClosedFloatingPointRange】
 *【valueRange】：最大最小范围【ClosedFloatingPointRange】
 *【steps】：步数【Int】
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RangeSliderBase() {
    var range by remember { mutableStateOf(-20f..20f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "当前值：${range.toRange()}")
        RangeSlider(
            value = range, onValueChange = {
                range = it
            },
            valueRange = -50f..50f,
            steps = 9
        )
    }
}

@Preview
@Composable
fun SliderPreview() {
    Column {
        SliderBase()
        SliderBaseWithStep()
        RangeSliderBase()
    }
}