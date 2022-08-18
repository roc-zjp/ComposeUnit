package com.zjp.compose_unit.ui.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.LocalFont
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.common.font_change_broadcast_action
import com.zjp.compose_unit.ui.theme.fontMap
import com.zjp.compose_unit.ui.theme.local


@Composable
fun FontSettingScreen(goBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            UnitTopAppBar(title = { Text(text = "字体设置 - Roc") }, navigationIcon = {
                IconButton(onClick = {
                    goBack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
            })
        }
    ) {
        var currentFont = LocalFont.current
        LazyVerticalGrid(columns = GridCells.Fixed(count = 2), modifier = Modifier.padding(it)) {
            items(fontMap.keys.toList()) { fontStr ->
                FontItem(fontStr = fontStr, currentFont == fontMap[fontStr])
            }
        }
    }
}

@Composable
fun FontItem(fontStr: String, isSelected: Boolean = false) {
    val colorList = arrayListOf(Color.White, Color.Red)
    val font = fontMap[fontStr] ?: local
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(colorList),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                context.sendBroadcast(Intent(font_change_broadcast_action).apply {
                    putExtra("font", fontStr)
                })
            }
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color(0x66000000)),

            ) {

            Text(
                text = fontStr,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(12.dp)
                        .background(Color.White, CircleShape)
                        .align(Alignment.CenterEnd)
                )
            }
        }

        Text(
            text = "天涯浪子\nRoc",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = font
        )
    }
}