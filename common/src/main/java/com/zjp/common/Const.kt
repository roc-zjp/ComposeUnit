package com.zjp.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily


val LocalThemeColor = compositionLocalOf { Color.Blue }

val LocalFont = compositionLocalOf { FontFamily.Default }


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