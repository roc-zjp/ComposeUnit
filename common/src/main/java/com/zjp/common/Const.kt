package com.zjp.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily


val LocalThemeColor = compositionLocalOf { Color.Blue }

val LocalFont = compositionLocalOf { FontFamily.Default as FontFamily  }
