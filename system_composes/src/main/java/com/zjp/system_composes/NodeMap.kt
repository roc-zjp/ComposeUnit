package com.zjp.system_composes

import androidx.compose.runtime.Composable
import com.zjp.system_composes.system.buttons.ButtonBase
import com.zjp.system_composes.system.buttons.*
import com.zjp.system_composes.system.containers.BoxBase
import com.zjp.system_composes.system.text.*
import com.zjp.system_composes.system.widgets.*
import com.zjp.system_composes.system.containers.*

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
        18 -> BoxBase()
        19 -> CardBase()
        20 -> LinearProgress()
        21 -> CircularProgress()
        22 -> SliderBase()
        23 -> SliderBaseWithStep()
        24 -> RangeSliderBase()

    }
}
