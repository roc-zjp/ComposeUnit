package com.zjp.compose_unit.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.zjp.compose_unit.ui.developer.Elevations

class Const {
    companion object {
        val tabColors = arrayListOf<Color>(
            Color(0xff44D1FD),
            Color(0xffFD4F43),
            Color(0xffB375FF),
            Color(0xFF4CAF50),
            Color(0xFFFF9800),
            Color(0xFF00F1F1),
            Color(0xFFDBD83F),
        )
        val colorLightBlue = Color(0xFF00F1F1)
        val colorDarkBlue = Color(0xff44D1FD)
        val colorRed = Color(0xffFD4F43)
        val colorPurple = Color(0xffB375FF)
        val colorGreen = Color(0xFF4CAF50)
        val colorOrange = Color(0xFFFF9800)
        val colorYellow = Color(0xFFFFEB3B)
    }
}

val palettes = arrayListOf<String>(
    "All",
    "Text",
    "Buttons",
    "Widgets",
    "Layouts",
    "Containers",
    "Helpers",
)


val themeColorSupport = mapOf<Color, String>(
    Color.Red to "毁灭之红",
    Color(0xFFFF9800) to "愤怒之橙",
    Color(0xFFFFEB3B) to "警告之黄",
    Color(0xFF4CAF50) to "伪装之绿",
    Color(0xFF2196F3) to "冷漠之蓝",
    Color(0xFF3F51B5) to "无限之靛",
    Color(0xFF9C27B0) to "神秘之紫",
    Color(0xff2D2D2D) to "归宿之黑"
)


val colorBlue = Color(0xFF2196F3)

val LocalThemeColor = compositionLocalOf { Color.Blue }

