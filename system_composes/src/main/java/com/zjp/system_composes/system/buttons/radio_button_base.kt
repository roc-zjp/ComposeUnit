package com.zjp.system_composes.system.buttons

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 基本使用
 * 【selected】：是否选中【Boolean】
 * 【onClick】：点击事件【Function】
 */
@Composable
fun RadioButtonBase() {
    var state by remember { mutableStateOf(true) }

    RadioButton(
        selected = state,
        onClick = { state = !state }
    )
}


/**
 * 自定义样式
 * 【colors】：选中时颜色、没选中时颜色和不可用时颜色【RadioButtonColors】
 */
@Composable
fun CustomRadioButton() {
    var state by remember { mutableStateOf(true) }
    Row {
        RadioButton(
            selected = state,
            onClick = { state = !state },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,
                unselectedColor = Color.Blue,
                disabledColor = Color.Gray
            )

        )
        RadioButton(
            selected = false,
            onClick = { },
            enabled = false,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,
                unselectedColor = Color.Blue,
                disabledColor = Color.Gray
            )

        )
    }
}


/**
 * 单选集合
 */
@Composable
fun RadioButtonGroup() {
    val tags = arrayListOf("Java", "Kotlin", "XML", "Compose", "JetPack")

    var selectIndex by remember { mutableStateOf(0) }
    Row(
        Modifier
            .selectableGroup()
            .height(20.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        tags.forEachIndexed { index, value ->
            RadioButton(
                selected = selectIndex == index,
                onClick = { selectIndex = index }
            )
            Text(text = value)
        }
    }
}

@Preview
@Composable
fun RadioButtonPreview() {

    Column {
        RadioButtonBase()
        RadioButtonGroup()
        CustomRadioButton()
    }
}