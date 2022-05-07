package com.zjp.compose_unit.code

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle


class CodeParse(val code: AnnotatedString) {

    companion object {
        val TAG = "CodeParse"
    }

    val builder: AnnotatedString.Builder = AnnotatedString.Builder(code)


    val results = arrayListOf<SpanBean>()
    val language = KotlinLanguage()

    fun parseCode(): List<SpanBean>? {
        var startIndex = 0
        var endIndex = code.length
        var result: MatchResult? = null
        Log.d(TAG, "length=${code.length}")
        while (startIndex != endIndex) {

            var commentRegex = Regex("\\*(.|\\n)*\\*/")
            result = commentRegex.find(code, startIndex)

            if (result != null) {
                results.add(
                    SpanBean(
                        result.range.first,
                        result.range.last,
                        result.value,
                        SpanType.comment
                    )
                )
                Log.d(TAG, "add comment value = ${result.value}")
                startIndex = result.range.last
                continue
            }

            var lineCommentRegex = Regex("//")
            result = lineCommentRegex.find(code, startIndex)
            if (result != null) {
                var lineCommentEndRegex = Regex(".*\\n")
                var endResult = lineCommentEndRegex.find(code, result.range.start)

                if (endResult != null) {
                    Log.d(
                        TAG,
                        "CodeParse lineComment start =${result.range.first},end=${endResult?.range.last}"
                    )

                    results.add(
                        SpanBean(
                            result.range.first,
                            endResult.range.last,
                            code.substring(result.range.first, endResult.range.last - 1),
                            SpanType.comment
                        )
                    )
                    startIndex = endResult.range.last
                    Log.d(
                        TAG,
                        "add comment value = ${
                            code.substring(
                                result.range.first,
                                endResult.range.last
                            )
                        }"
                    )
                    continue
                }
            }

            var keywordRegex = Regex("\\w+");
            var result = keywordRegex.find(code, startIndex + 1);
            if (result != null) {
                if (language.containsKeywords(result.value)) {
                    results.add(
                        SpanBean(
                            result.range.first,
                            result.range.last,
                            result.value,
                            SpanType.keyword
                        )
                    )
                    Log.d(TAG, "add keyword value = ${result.value}")
                }
                Log.d(TAG, "word=${result.value}, startIndex=$startIndex,end=${result.range.last}")
                startIndex = result.range.last
                continue
            }

            startIndex = code.length

        }

        return results
    }



    fun toAnnotatedString(): AnnotatedString {
        var commentRegex = Regex("\\*(.|\\n)*\\*/")
        var results = commentRegex.findAll(code)
        results?.let {
            it.forEach { match ->
                builder.addStyle(SpanStyle(color = Color.Red), match.range.first, match.range.last)
            }
        }
        return builder.toAnnotatedString()
    }

}