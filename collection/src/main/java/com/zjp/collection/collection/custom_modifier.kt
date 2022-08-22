package com.zjp.collection.collection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import kotlin.math.max


fun Modifier.customLayoutModifier() = layout { measurable, constraints ->
    layout(100, 100) {

    }
}


@Composable
fun CollapsingToolbarLayout(
    appbar: @Composable () -> Unit,
    body: @Composable () -> Unit
) {


    Layout(content = {
        appbar()
        body()
    }) { measurables, constraints ->

        val offsetY = mutableStateOf(0)

        val toolbarConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0
        )

        val toolbarPlaceable = measurables[0].measure(toolbarConstraints)

        val bodyMeasurables = measurables.subList(1, measurables.size)
        val bodyPlaceables = bodyMeasurables.map {
            it.measure(constraints)
        }

        val height = max(toolbarPlaceable.height, bodyPlaceables.maxOf { it.height })

        val width = max(toolbarPlaceable.width, bodyPlaceables.maxOf { it.width })

        layout(width, height) {
            toolbarPlaceable.placeRelative(0, 0)
            bodyPlaceables.forEach {
                it.placeRelative(0, 0)
            }
        }
    }
}