//package com.zjp.compose_unit
//
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Outline
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.unit.Density
//import androidx.compose.ui.unit.LayoutDirection
//
//
//class TechnoOutlineShape(
//    private val cornerWidth: Float = 10.0f,
//    private val spanWidth: Float = 5f,
//    private val innerRate: Float = 0.15f,
//    private val storkWidth: Float = 1.0f
//) : Shape {
//
//    override fun createOutline(
//        size: Size,
//        layoutDirection: LayoutDirection,
//        density: Density
//    ): Outline {
//        val path = Path().apply {
//            moveTo(cornerWidth, 0f)
//            relativeLineTo(size.width - cornerWidth * 2, 0f)
//            relativeLineTo(cornerWidth, cornerWidth)
//            relativeLineTo(0f, size.height - cornerWidth * 2)
//            relativeLineTo(-cornerWidth, cornerWidth)
//            relativeLineTo(
//                -((size.width - innerRate * 2 * size.width) / 2 - cornerWidth - 2 * spanWidth),
//                0f
//            )
//            relativeLineTo(-spanWidth * 2, -spanWidth)
//            relativeLineTo(-size.width * innerRate * 2, 0f)
//            relativeLineTo(-spanWidth * 2, spanWidth)
//            relativeLineTo(
//                -((size.width - innerRate * 2 * size.width) / 2 - cornerWidth - 2 * spanWidth),
//                0f
//            )
//            lineTo(0f, size.height - cornerWidth)
//            lineTo(0f, cornerWidth)
//            close();
//        }
//        return Outline.Generic(path)
//
//    }
//
//}