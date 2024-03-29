package com.zjp.compose_unit.common.shape

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TechnoShapeBorder(
    var color: Color = Color.Green,
    var cornerWidth: Float = 10.0f,
    var spanWidth: Float = 2.5f,
    private var innerRate: Float = 0.15f,
    var storkWidth: Float = 1.0f

) : Shape {
    private var outLinePath = Path()
    private var innerLinePath = Path()


    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
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

        outLinePath.addRect(Rect(200f, 200f, 200f, 200f))


//        innerLinePath
//            .moveTo(size.width / 2, 0f)
//        innerLinePath.relativeLineTo(size.width * innerRate, 0f)
//        innerLinePath.relativeLineTo(-spanWidth * 2, spanWidth)
//        innerLinePath.relativeLineTo(-size.width * innerRate * 2, 0f)
//        innerLinePath.relativeLineTo(-spanWidth * 2, -spanWidth)
//        innerLinePath.close()
//
//        outLinePath.addPath(innerLinePath)

        return Outline.Generic(outLinePath)
    }


}