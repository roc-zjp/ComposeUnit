package com.zjp.compose_unit.compose_system

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NotFound() {
    Text(text = "没有找到Compose", modifier = Modifier
        .width(100.dp)
        .height(100.dp))
}