package com.zjp.common.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

class SpanBean(val start: Int, val end: Int, private val value: String, val type: SpanType) {

    fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append(value)
            }
        }
    }

}