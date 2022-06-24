package com.zjp.compose_unit

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

 class TechnoInlineShape(
    private val spanWidth: Float = 5f,
    private val innerRate: Float = 0.15f,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo((size.width - size.width * innerRate) / 2, 0f)
            relativeLineTo(size.width * innerRate, 0f)
            relativeLineTo(-spanWidth, spanWidth)
            relativeLineTo(-(size.width * innerRate - spanWidth * 2), 0f)
            close();
        }
        return Outline.Generic(path)
    }


}