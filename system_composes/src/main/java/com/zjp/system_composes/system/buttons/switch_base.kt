package com.zjp.system_composes.system.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 基本使用
 * 【checked】：选中状态【Boolean】
 * 【onCheckedChange】：状态改变时的回调【Function】
 * 【colors】：自定义颜色【SwitchColors】
 */
@Composable
fun SwitchBase() {
    var checked by remember { mutableStateOf(false) }
    var customChecked by remember { mutableStateOf(false) }

    Row {
        Row(modifier = Modifier.height(20.dp)) {
            Row {
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }
                )
                Text(text = "默认样式")
            }
            Row(modifier = Modifier.height(20.dp)) {
                Switch(
                    checked = customChecked, onCheckedChange = {
                        customChecked = it
                    }, colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Red,
                        uncheckedThumbColor = Color.Green,
                        uncheckedTrackColor = Color.Green
                    )
                )
                Text(text = "自定义样式")
            }
        }
    }
}

@Preview
@Composable
fun SwitchPreview() {
    SwitchBase()
}