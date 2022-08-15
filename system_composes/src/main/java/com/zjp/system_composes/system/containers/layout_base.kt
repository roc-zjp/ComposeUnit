package com.zjp.system_composes.system.containers

import android.graphics.Point
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import com.google.android.material.math.MathUtils.lerp
import kotlin.math.roundToInt

@Composable
fun LayoutBase() {
    Layout(content = {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}


@Composable
fun HorizontalLayout() {
    Layout(content = {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var xPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = xPosition, y = 0)

                // Record the y co-ord placed up to
                xPosition += placeable.width
            }
        }
    }
}

@Composable
fun VerticalLayout() {
    Layout(content = {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun WrapLayoutSample() {

    Layout(content = {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }
        var withd = 0
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
            withd = maxOf(withd, xPosition)
            maxHeight = maxOf(maxHeight, placeable.height)
        }
        height = maxOf(height, yPosition + maxHeight)

        layout(withd, height) {
            placeables.forEachIndexed() { index, placeable ->
                val point = locations[index]

                placeable.placeRelative(x = point.x, y = point.y)

            }
        }
    }

}





