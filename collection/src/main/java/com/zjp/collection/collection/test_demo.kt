package com.zjp.collection.collection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.zjp.collection.R
import kotlin.math.roundToInt

@Composable
fun CollapsingToolbarLayout() {

    val minHeightPx = 0f
    val maxHeightPx = with(LocalDensity.current) { 200.dp.roundToPx().toFloat() }

    var imageHeight by remember {
        mutableStateOf(maxHeightPx)
    }

    val scrollState = rememberLazyListState()

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.y
            val newHeight = (imageHeight + delta).coerceIn(minHeightPx, maxHeightPx)
            val consumed = newHeight - imageHeight
            imageHeight = newHeight
            return Offset(0f, consumed)
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val delta = available.y / 10
            val newHeight = (imageHeight + delta).coerceIn(minHeightPx, maxHeightPx)
            val consumed = newHeight - imageHeight
            imageHeight = newHeight
            return Velocity(0f, consumed)
        }
    }


    Layout(content = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { imageHeight.toDp() })
        ) {
            Image(
                painter = painterResource(id = R.drawable.images),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {

                    }

            )
        }

        LazyColumn(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            state = scrollState
        ) {
            items(50) { index ->
                Text(
                    text = "item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

    }) { measurables, constraints ->
        check(measurables.size == 2)
        val appbarplace = measurables[0].measure(constraints)
        val bodyplace = measurables[1].measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) {
            appbarplace.placeRelative(0, 0)
            bodyplace.placeRelative(0, imageHeight.roundToInt())
        }
    }


}