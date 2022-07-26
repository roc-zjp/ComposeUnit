package com.zjp.system_composes.system.widgets

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apkfuns.logutils.LogUtils
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun CanvasBase() {
    Canvas(
        modifier = Modifier.size(300.dp),
        onDraw = {
            // Head
            drawCircle(
                Brush.linearGradient(
                    colors = listOf(Color.Green, Color.Gray)
                ),
                radius = size.width / 2,
                center = center,
                style = Stroke(width = size.width * 0.075f)
            )

            // Smile
            val smilePadding = size.width * 0.15f
            drawArc(
                color = Color.Red,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                topLeft = Offset(smilePadding, smilePadding),
                size = Size(size.width - (smilePadding * 2f), size.height - (smilePadding * 2f))
            )

            // Left eye
            drawRect(
                color = Color.Black,
                topLeft = Offset(size.width * 0.25f, size.height / 4),
                size = Size(smilePadding, smilePadding)
            )

            // Right eye
            drawRect(
                color = Color.Black,
                topLeft = Offset((size.width * 0.75f) - smilePadding, size.height / 4),
                size = Size(smilePadding, smilePadding)
            )
        }
    )
}

//@Composable
//fun CanvasAnimation() {
//    val deltaXAnim = rememberInfiniteTransition()
//    val dx by deltaXAnim.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(500, easing = LinearEasing)
//        )
//    )
//
//    val screenWidthPx = with(LocalDensity.current) {
//        (LocalConfiguration.current.screenHeightDp * density)
//    }
//    val animTranslate by animateFloatAsState(
//        targetValue = screenWidthPx,
//        animationSpec = TweenSpec(10000, easing = LinearEasing)
//    )
//
//    val waveHeight by animateFloatAsState(
//        targetValue = 0f,
//        animationSpec = TweenSpec(10000, easing = LinearEasing)
//    )
//
//    Canvas(
//        modifier = Modifier.size(300.dp),
//        onDraw = {
//            translate(top = animTranslate) {
//                drawPath(path = path, color = animColor)
//                path.reset()
//                val halfWaveWidth = waveWidth / 2
//                path.moveTo(-waveWidth + (waveWidth * dx), originalY.dp.toPx())
//
//                for (i in -waveWidth..(size.width.toInt() + waveWidth) step waveWidth) {
//                    path.relativeQuadraticBezierTo(
//                        halfWaveWidth.toFloat() / 2,
//                        -waveHeight,
//                        halfWaveWidth.toFloat(),
//                        0f
//                    )
//                    path.relativeQuadraticBezierTo(
//                        halfWaveWidth.toFloat() / 2,
//                        waveHeight,
//                        halfWaveWidth.toFloat(),
//                        0f
//                    )
//                }
//
//                path.lineTo(size.width, size.height)
//                path.lineTo(0f, size.height)
//                path.close()
//            }
//        }
//    )
//
//}

@Composable
fun CanvasInset() {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red)
    ) {
        val canvasQuadrantSize = size / 2F
        inset(50F, 30F) {
            drawRect(
                color = Color.Green,
                size = canvasQuadrantSize
            )
        }
    }
}


@Composable
fun CanvasRotate() {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red)
    ) {
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height
        rotate(degrees = 45F) {
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F, y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }
}

@Composable
fun CanvasTransform() {
    Canvas(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red)
    ) {
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height
        withTransform({
            translate(left = canvasWidth / 5F)
            rotate(degrees = 45F)
        }) {
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F, y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }
}


@Composable
fun DrawClock() {
    var date by remember {
        mutableStateOf(Date())
    }

    LaunchedEffect(key1 = true) {
        while (true) {
            delay(1000)
            date = Date()
        }
    }
    Canvas(modifier = Modifier.size(500.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = canvasWidth / 4
        drawCircle(
            color = Color.Blue,
            radius = radius,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            style = Stroke(width = 5f)
        )
        drawClockText()
        drawGraduation()
        drawCircle(color = Color.Green, radius = 10f)
        drawPointers(date)
    }
}

fun DrawScope.drawClockText() {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val radius = canvasWidth / 4
    inset(left = canvasWidth / 2, top = canvasHeight / 2, right = 0f, bottom = 0f) {
        drawText("Ⅲ", x = radius)
        drawText("Ⅸ", x = -radius)
        drawText("Ⅵ", y = radius)
        drawText("Ⅻ", y = -radius)
    }
}

fun DrawScope.drawText(text: String, x: Float = 0f, y: Float = 0f) {
    val textPaint = TextPaint()
    textPaint.isAntiAlias = true
    textPaint.color = Color.Blue.toArgb()
    textPaint.textSize = 20.sp.toPx()
    val textWidth = textPaint.measureText(text)
    val textTop = textPaint.fontMetrics.top
    val textBottom = textPaint.fontMetrics.bottom

    drawRect(
        color = Color.White,
        topLeft = Offset(x = x - textWidth / 2, y - (textBottom - textTop) / 2),
        size = Size(textWidth, textBottom - textTop)
    )
    drawContext.canvas.nativeCanvas.drawText(
        text,
        x - textWidth / 2,
        y - (textBottom + textTop) / 2,
        textPaint
    )
}

fun DrawScope.drawGraduation() {
    val canvasWidth = size.width
    val canvasHeight = size.height
    val radius = (size.width - 160.dp.value) / 4
    var length = 20
    for (i in 0..59) {
        rotate(6f * i) {
            inset(left = canvasWidth / 2, top = canvasHeight / 2, right = 0f, bottom = 0f) {
                if (i % 5 == 0) {
                    length = 20.dp.value.toInt()
                    drawLine(
                        color = Color.Blue,
                        start = Offset(x = 0f, y = radius - length),
                        end = Offset(x = 0f, y = radius),
                        strokeWidth = 4f
                    )
                    drawCircle(
                        color = Color.Yellow,
                        radius = 2f,
                        center = Offset(x = 0f, y = radius - length - 10)
                    )
                } else {
                    length = 10.dp.value.toInt()
                    drawLine(
                        color = Color.Black,
                        start = Offset(x = 0f, y = radius - length),
                        end = Offset(x = 0f, y = radius),
                        strokeWidth = 2f
                    )
                }
            }
        }
    }
}

fun DrawScope.drawPointer(degrees: Float, radius: Float, strokeWith: Float) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    rotate(degrees) {
        inset(left = canvasWidth / 2, top = canvasHeight / 2, right = 0f, bottom = 0f) {
            drawLine(
                color = Color.Green,
                start = Offset(0f, 0f),
                end = Offset(0f, -radius),
                strokeWidth = strokeWith
            )
        }
    }
}

fun DrawScope.drawPointers(date: Date) {
    val calendar = Calendar.getInstance()
    calendar.time = date //放入Date类型数据

    val hour = calendar[Calendar.HOUR] //时（12小时制）

    val minute = calendar[Calendar.MINUTE] //分

    val second = calendar[Calendar.SECOND]


    val hourRadius = (size.width - 800.dp.value) / 4
    val minusRadius = (size.width - 600.dp.value) / 4
    val secRadius = (size.width - 400.dp.value) / 4
    val hourStrokeWidth = 8f
    val minusStrokeWidth = 6f
    val secStrokeWidth = 4f

    drawPointer(second * 6f, secRadius, secStrokeWidth)
    drawPointer(minute * 6f + second / 10, minusRadius, minusStrokeWidth)
    drawPointer(hour * 30f + minute / 2 + second / 12, hourRadius, hourStrokeWidth)

}


@Preview
@Composable
fun ClockPre() {
    DrawClock()
}

