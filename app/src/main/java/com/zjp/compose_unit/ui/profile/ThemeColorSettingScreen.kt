package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.common.themeColorSupport
import com.zjp.compose_unit.ui.OnThemeColorChange

@Composable
fun ThemeColorSettingScreen(onThemeColorChange: OnThemeColorChange, goBack: () -> Unit = {}) {
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
                    ThemeItem(color = color, currentColor == color, onThemeColorChange)
                }
            })
    }
}

@Composable
fun ThemeItem(color: Color, isSelected: Boolean = false, onThemeColorChange: OnThemeColorChange) {
    val colorList = arrayListOf(color.copy(alpha = 0.1f), color.copy(alpha = 1.0f))
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(colorList),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onThemeColorChange(color) }
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