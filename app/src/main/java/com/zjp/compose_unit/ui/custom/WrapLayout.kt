package com.zjp.compose_unit.ui.custom

import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import com.apkfuns.logutils.LogUtils

@Composable
fun WrapLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        var withd = 0
        var height = 0

        var yPosition = 0
        var xPosition = 0
        var maxHeight = 0

        var locations = mutableListOf<Point>()


        placeables.forEach { placeable ->
            if (xPosition + placeable.width > constraints.maxWidth) {
                yPosition += maxHeight
                xPosition = 0
                maxHeight = 0
            }

            locations.add(Point(xPosition, yPosition))
            xPosition += placeable.width
            withd = maxOf(withd, xPosition)
            maxHeight = maxOf(maxHeight, placeable.height)
        }

        height = maxOf(height, yPosition + maxHeight)
        LogUtils.d("set width=$withd,set height=$height")


        // Set the size of the layout as big as it can
        layout(withd, height) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0
            var xPosition = 0
            var maxHeight = 0

            LogUtils.d("maxWidth=${constraints.maxWidth},maxHeight=${constraints.maxHeight}")

            placeables.forEachIndexed() { index, placeable ->
                var point = locations[index]

                placeable.placeRelative(x = point.x, y = point.y)

            }
        }
    }
}




