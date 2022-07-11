package com.zjp.system_composes

import androidx.compose.runtime.Composable
import com.zjp.system_composes.system.animation.*
import com.zjp.system_composes.system.buttons.ButtonBase
import com.zjp.system_composes.system.buttons.*
import com.zjp.system_composes.system.containers.BoxBase
import com.zjp.system_composes.system.text.*
import com.zjp.system_composes.system.widgets.*
import com.zjp.system_composes.system.containers.*
import com.zjp.system_composes.system.helper.DividerBase

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
        25 -> DividerBase()
        26 -> RowBase()
        27 -> ColumnBase()
        28 -> SpacerBase()
        29 -> IconBase()
        31 -> LazyColumnBase()
        32 -> LazyColumnWithKey()
        33 -> LazyRowBase()
        34 -> LazyRowWithKey()
        35 -> SimpleFilledTextFieldSample()
        36 -> PasswordTextField()
        37 -> LazyVerticalGridBase()
        38 -> LazyVerticalGridFixed()
        39 -> AnimatedVisibilityBase()
        40 -> AnimatedVisibilitySpring()
        41 -> AnimatedMutableTransitionState()
        42 -> ChildAnimated()
        43 -> AnimatedTransition()
        44 -> AnimatedContentBase()
        45 -> AnimatedContentWithContentTransform()
        46 -> AnimatedContentSizeTransform()
        47 -> ModifierContentAnimated()
        48 -> AnimatedContentChild()
        49 -> CrossfadeBase()
        50 -> TableRowBase()
        51 -> OutlinedButtonBase()
        52 -> ButtonClearPadding()
    }
}
