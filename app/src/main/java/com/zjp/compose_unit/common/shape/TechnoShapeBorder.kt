package com.zjp.compose_unit.common.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TechnoShapeBorder(
    var color: Color = Color.Green,
    var cornerWidth: Float = 10.0f,
    var spanWidth: Float = 2.5f,
    var innerRate: Float = 0.15f,
    var storkWidth: Float = 1.0f

) : Shape {

    var outLinePath = Path();
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        var width = size.width;
        outLinePath.moveTo(cornerWidth, 0f)
        outLinePath.relativeLineTo(width - cornerWidth * 2, 0f)
        outLinePath.relativeLineTo(cornerWidth, cornerWidth)
        outLinePath.relativeLineTo(0f, size.height - cornerWidth * 2)
        outLinePath.relativeLineTo(-cornerWidth, cornerWidth)
        outLinePath.relativeLineTo(
            -((width - innerRate * 2 * width) / 2 - cornerWidth - 2 * spanWidth),
            0f
        )
        outLinePath.relativeLineTo(-spanWidth * 2, -spanWidth)
        outLinePath.relativeLineTo(-size.width * innerRate * 2, 0f)
        outLinePath.relativeLineTo(-spanWidth * 2, spanWidth)
        outLinePath.relativeLineTo(
            -((width - innerRate * 2 * width) / 2 - cornerWidth - 2 * spanWidth),
            0f
        )
        outLinePath.lineTo(0f, size.height - cornerWidth)
        outLinePath.lineTo(0f, cornerWidth)
        outLinePath.close()



        return Outline.Generic(outLinePath)
    }

}