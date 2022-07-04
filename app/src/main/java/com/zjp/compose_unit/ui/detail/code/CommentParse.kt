package com.zjp.compose_unit.ui.detail.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

class CommentParse(var code: AnnotatedString) {
    private val builder: AnnotatedString.Builder = AnnotatedString.Builder(code)

    fun toAnnotatedString(): AnnotatedString {
        val commentRegex = Regex("\\*(.|\\n)*\\*/")
        val results = commentRegex.findAll(code)
        results?.let {
            it.forEach { match ->
                builder.addStyle(SpanStyle(color = Color.Gray), match.range.first, match.range.last)
            }
        }
        return builder.toAnnotatedString()
    }
}