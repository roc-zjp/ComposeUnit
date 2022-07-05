package com.zjp.system_composes.system.helper

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 *【thickness】：粗细【DP】
 *【color】：颜色【Color】
 *【startIndent】：开始空缺长度【DP】
 */
@Composable
fun DividerBase() {
    Divider(
        thickness = 5.dp,
        color = Color.Magenta,
        startIndent = 20.dp,
    )
}

@Preview
@Composable
fun DividerPreview() {
    DividerBase()
}