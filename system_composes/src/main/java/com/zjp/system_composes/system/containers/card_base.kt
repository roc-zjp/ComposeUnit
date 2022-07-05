package com.zjp.system_composes.system.containers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 【onClick】：点击卡片时调用的回调
 * 【modifier】：应用于卡片布局的修饰符。
 * 【shape】：定义卡片的形状及其阴影。elevation仅当大于零时才会显示阴影
 * 【backgroundColor】：背景颜色。
 * 【border】：边框
 * 【elevation】：阴影的大小
 * 【interactionSource】：Interaction您可以创建并传递自己的记忆
 * 【indication】：按下卡时显示的指示【LocalIndication】
 * 【role】：用户界面元素的类型。可访问性服务可能会使用它来描述元素或进行自定义。例如，如果卡片充当按钮，您应该通过Role.Button
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardBase() {
    var count by remember { mutableStateOf(0) }
    Card(
        onClick = { count++ },
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        border = BorderStroke(width = 1.dp, color = Color.Red),
        elevation = 10.dp
    ) {
        Text("Clickable card content with count: $count")
    }
}


@Preview(showSystemUi = true)
@Composable
fun CardPreview() {
    CardBase()
}