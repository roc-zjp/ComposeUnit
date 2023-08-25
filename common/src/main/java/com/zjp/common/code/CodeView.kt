package com.zjp.common.code

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CodeView(code: String, modifier: Modifier = Modifier) {
    val state = rememberScrollState()
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.LightGray)
            .padding(10.dp)
            .horizontalScroll(state)

    ) {
//        var formatCode =
//            AnnotatedString(code).formatCode()
        Text(text = code)
    }
}


