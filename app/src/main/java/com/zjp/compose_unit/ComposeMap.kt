package com.zjp.compose_unit

import androidx.compose.runtime.Composable
import com.zjp.compose_unit.compose_system.NotFound

import com.zjp.compose_unit.compose_system.text.AnnotatedClickableText
import com.zjp.compose_unit.compose_system.text.PartiallySelectableText
import com.zjp.compose_unit.compose_system.text.RichText
import com.zjp.compose_unit.compose_system.text.TextCommon


@Composable
fun nodeMap(id: Int) {
    when (id) {
        1 -> TextCommon()
        2 -> RichText()
        3 -> PartiallySelectableText()
        4 -> AnnotatedClickableText()
        else -> NotFound()
    }
}