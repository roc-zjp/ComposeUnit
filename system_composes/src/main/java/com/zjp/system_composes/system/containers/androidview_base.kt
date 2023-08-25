package com.zjp.system_composes.system.containers

import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AndroidViewBase() {
    AndroidView(factory = { context ->
        TextView(context).apply {
            text = " 原生Android View的文本控件"
        }
    })
}


@Composable
fun ComposeViewBase() {
    val context = LocalContext.current
    AndroidView(factory = { context ->
        FrameLayout(context).apply {
            addView(ComposeView(context).apply {
                setContent {
                    Column {
                        Text(text = "这是compose text组合")
                    }
                }
            })
        }
    })
}


@Composable
fun BackHandlerBase() {

    var intercept by remember {
        mutableStateOf(false)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = intercept, onCheckedChange = {
                intercept = it
            }
        )
        Text(text = "是否拦截返回事件")
    }

    val context = LocalContext.current

    BackHandler(enabled = intercept) {
        Toast.makeText(context, "返回事件已被拦截", Toast.LENGTH_SHORT).show()
    }
}