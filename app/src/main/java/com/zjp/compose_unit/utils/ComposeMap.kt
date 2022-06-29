package com.zjp.compose_unit.utils

import androidx.compose.runtime.Composable
import com.zjp.compose_unit.compose_system.NotFound

import com.zjp.compose_unit.compose_system.text.AnnotatedClickableText
import com.zjp.compose_unit.compose_system.text.PartiallySelectableText
import com.zjp.compose_unit.compose_system.text.RichText
import com.zjp.compose_unit.compose_system.text.TextCommon
import com.zjp.compose_unit.compose_system.widgets.ImageBase
import com.zjp.compose_unit.compose_system.widgets.ImageBlendMode
import com.zjp.compose_unit.compose_system.widgets.ImageContentScaleType
import com.zjp.compose_unit.compose_system.widgets.ImageQuality


@Composable
fun nodeMap(id: Int) {
    when (id) {
        1 -> TextCommon()
        2 -> RichText()
        3 -> PartiallySelectableText()
        4 -> AnnotatedClickableText()
        5 -> ImageBase()
        6 -> ImageContentScaleType()
        7 -> ImageQuality()
        8 -> ImageBlendMode()
        else -> NotFound()
    }
}