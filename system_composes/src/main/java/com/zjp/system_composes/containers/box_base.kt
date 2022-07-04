package com.zjp.system_composes.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjp.system_composes.custom_compose.WrapLayout

/**
 * Box基本使用
 * 【modifier】：装饰【Modifier】
 * 【contentAlignment】：内容对齐方式【Alignment】
 */
@Composable
fun BoxBase() {
    val contentAlignments = listOf<Alignment>(
        Alignment.TopStart,
        Alignment.TopCenter,
        Alignment.TopEnd,
        Alignment.CenterStart,
        Alignment.Center,
        Alignment.CenterEnd,
        Alignment.BottomStart,
        Alignment.BottomCenter,
        Alignment.BottomEnd,
    )
    WrapLayout() {
        contentAlignments.forEach { aligment ->
            Box(
                Modifier
                    .padding(10.dp)
                    .background(Color.Red)
                    .width(100.dp)
                    .height(100.dp),
                contentAlignment = aligment
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .background(Color.Green)
                )
            }
        }
    }
}

@Preview
@Composable
fun BoxPreview() {
    BoxBase()
}
