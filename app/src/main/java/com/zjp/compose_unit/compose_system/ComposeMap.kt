package com.zjp.compose_unit.compose_system

import androidx.compose.runtime.Composable
import com.zjp.compose_unit.compose_system.text.*


@Composable
fun map(name: String): ArrayList<Unit> {
    when (name) {
        "text" -> {
            return arrayListOf(
                TextCommon(),
                TextMaxLine(),
                RichText(),
                PartiallySelectableText(),
                AnnotatedClickableText()
            )
        }
        "container" -> {
            return arrayListOf(TextCommon())
        }
    }
    return arrayListOf(TextCommon())
}


