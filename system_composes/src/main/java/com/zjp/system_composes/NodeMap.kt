package com.zjp.system_composes

import androidx.compose.runtime.Composable
import com.zjp.system_composes.buttons.ButtonBase
import com.zjp.system_composes.buttons.*
import com.zjp.system_composes.text.*
import com.zjp.system_composes.widgets.*

@Composable
fun NodeMap(id: Int) {
    when (id) {
        1 -> TextCommon()
        2 -> RichText()
        3 -> PartiallySelectableText()
        4 -> AnnotatedClickableText()
        5 -> ImageBase()
        6 -> ImageContentScaleType()
        7 -> ImageQuality()
        8 -> ImageBlendMode()
        9 -> CornerImage()
        10 -> ButtonBase()
        11 -> ButtonWithMultipleText()
        12 -> ButtonWithIcon()
        13 -> TextButtonBase()
        14 -> RadioButtonBase()
        15 -> CustomRadioButton()
        16 -> RadioButtonGroup()
        17 -> SwitchBase()

    }
}