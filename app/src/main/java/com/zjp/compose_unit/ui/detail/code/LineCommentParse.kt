package com.zjp.compose_unit.ui.detail.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

class LineCommentParse(val code: AnnotatedString) {

    private val builder: AnnotatedString.Builder = AnnotatedString.Builder(code)

    fun toAnnotatedString(): AnnotatedString {
        val commentRegex = Regex("//")
        val results = commentRegex.findAll(code)
        results?.let {
            it.forEach { match ->
                val commentEndRegex = Regex(".*\\n")
                val commentEnd = commentEndRegex.find(code, match.range.first)
                var endIndex = 0
                endIndex = commentEnd?.range?.last ?: code.length
                builder.addStyle(
                    SpanStyle(color = Color.Red),
                    match.range.first,
                    endIndex
                )
            }
        }
        return builder.toAnnotatedString()
    }
}