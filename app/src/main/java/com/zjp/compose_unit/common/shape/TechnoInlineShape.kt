package com.zjp.compose_unit.common.shape

import androidx.compose.ui.geometry.Offset
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

    var innerLinePathTop = Path();
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        innerLinePathTop
            .moveTo(size.width / 2, 0f)
        innerLinePathTop.relativeLineTo(size.width * innerRate, 0f)
        innerLinePathTop.relativeLineTo(-spanWidth * 2, spanWidth)
        innerLinePathTop.relativeLineTo(-size.width * innerRate * 2, 0f)
        innerLinePathTop.relativeLineTo(-spanWidth * 2, -spanWidth)
        innerLinePathTop.close()
        innerLinePathTop.translate(Offset(spanWidth * 2, 0f))
        return Outline.Generic(innerLinePathTop)
    }


}