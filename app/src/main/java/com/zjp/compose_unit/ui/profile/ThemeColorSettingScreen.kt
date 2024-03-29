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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.common.color_change_broadcast_action
import com.zjp.compose_unit.common.themeColorSupport


@Composable
fun ThemeColorSettingScreen(goBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            UnitTopAppBar(title = { Text(text = "主题设置") }, navigationIcon = {
                IconButton(onClick = {
                    goBack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
            })
        }
    ) {
        var currentColor = LocalThemeColor.current
        LazyVerticalGrid(columns = GridCells.Fixed(count = 2),
            modifier = Modifier.padding(it),
            content = {
                items(themeColorSupport.keys.toList()) { color ->
                    ThemeItem(color = color, currentColor == color)
                }
            })
    }
}

@Composable
fun ThemeItem(color: Color, isSelected: Boolean = false) {
    val colorList = arrayListOf(color.copy(alpha = 0.1f), color.copy(alpha = 1.0f))
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
                context.sendBroadcast(Intent(color_change_broadcast_action).apply {
                    putExtra("color", color.toArgb())
                })
            }
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(Color(0x66000000)),

            ) {
            val hexColor = "#${Integer.toHexString(color.toArgb()).uppercase()}"
            Text(
                text = hexColor,
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

        themeColorSupport[color]?.let {
            Text(
                text = it,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}