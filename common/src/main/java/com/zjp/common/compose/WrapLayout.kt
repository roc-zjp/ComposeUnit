package com.zjp.common.compose

import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

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
        var width = 0
        var height = 0

        var yPosition = 0
        var xPosition = 0
        var maxHeight = 0

        val locations = mutableListOf<Point>()

        placeables.forEach { placeable ->
            if (xPosition + placeable.width > constraints.maxWidth) {
                yPosition += maxHeight
                xPosition = 0
                maxHeight = 0
            }

            locations.add(Point(xPosition, yPosition))
            xPosition += placeable.width
            width = maxOf(width, xPosition)
            maxHeight = maxOf(maxHeight, placeable.height)
        }
        height = maxOf(height, yPosition + maxHeight)

        layout(width, height) {
            placeables.forEachIndexed() { index, placeable ->
                val point = locations[index]

                placeable.placeRelative(x = point.x, y = point.y)

            }
        }
    }
}




