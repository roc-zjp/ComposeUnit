package com.zjp.compose_unit.ui.detail.code

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

class KeyWordParse(val code: AnnotatedString) {
    private val builder: AnnotatedString.Builder = AnnotatedString.Builder(code)

    fun toAnnotatedString(): AnnotatedString {
        var keyWordRegex = Regex("\\w+'")
        var results = keyWordRegex.findAll(code)
        results?.let {
            it.forEach { match ->
                var commentEndRegex = Regex(".*\\n")
                var commentEnd = commentEndRegex.find(code, match.range.first)
                var endIndex = 0
                endIndex = commentEnd?.range?.last ?: code.length
                builder.addStyle(
                    SpanStyle(color = Color.Green),
                    match.range.first,
                    endIndex
                )
            }
        }
        return builder.toAnnotatedString()
    }


}

fun AnnotatedString.highlightCommend(): AnnotatedString {
    val builder: AnnotatedString.Builder = AnnotatedString.Builder(this)
    var commentRegex = Regex("\\*(.|\\n)*\\*/")
    var results = commentRegex.findAll(this)
    results?.let {
        it.forEach { match ->
            builder.addStyle(
                SpanStyle(color = Color.Green),
                match.range.first - 1,
                match.range.last + 1
            )
        }
    }
    return builder.toAnnotatedString()
}

fun AnnotatedString.highlightLineCommend(): AnnotatedString {
    val builder: AnnotatedString.Builder = AnnotatedString.Builder(this)
    var keyWordRegex = Regex("//")
    var results = keyWordRegex.findAll(this)
    results?.let {
        it.forEach { match ->
            var commentEndRegex = Regex(".*\\n")
            var commentEnd = commentEndRegex.find(this, match.range.first)
            var endIndex = 0
            endIndex = commentEnd?.range?.last ?: this.length
            builder.addStyle(
                SpanStyle(color = Color.Green),
                match.range.first,
                endIndex
            )
        }
    }
    return builder.toAnnotatedString()
}

fun AnnotatedString.highlightKeyWord(): AnnotatedString {
    val builder: AnnotatedString.Builder = AnnotatedString.Builder(this)
    var keyWordRegex = Regex("\\w+")
    var results = keyWordRegex.findAll(this)
    var language = KotlinLanguage()
    Log.d(TAG, "${results?.count() ?: 0}")
    results?.let {

        Log.d(TAG, "first range =${results.first().range}")

        it.forEach { match ->
            Log.d(TAG, match.value)
            if (language.containsKeywords(match.value)) {
                builder.addStyle(
                    SpanStyle(color = Color.Red),
                    match.range.first,
                    match.range.last + 1
                )
            }
        }
    }
    return builder.toAnnotatedString()
}


fun AnnotatedString.formatCode(): AnnotatedString {

    return this.highlightKeyWord().highlightCommend().highlightLineCommend()
}


val TAG = "CodeParse"
