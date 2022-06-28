package com.zjp.compose_unit.common

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize


@Composable
fun painterWebpResource(@DrawableRes id: Int): Painter {
    val context = LocalContext.current
    val res = context.resources
    val value = remember { TypedValue() }
    res.getValue(id, value, true)
    val path = value.string
    // Assume .xml suffix implies loading a VectorDrawable resource
    return if (path?.endsWith(".webp") == true) {
        val imageBitmap = remember(path, id) {
            loadImageBitmapResource(res, id)
        }
        WebpBitmapPainter(imageBitmap)
    } else {
        painterResource(id = id)
    }
}

private fun loadImageBitmapResource(res: Resources, id: Int): ImageBitmap {
    try {
        return ImageBitmap.imageResource(res, id)
    } catch (throwable: Throwable) {
        throw IllegalArgumentException(throwable.message)
    }
}

class WebpBitmapPainter(
    private val image: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height)
) : Painter() {
    private val size: IntSize = validateSize(srcOffset, srcSize)

    private var alpha: Float = 1.0f

    private var colorFilter: ColorFilter? = null

    override fun DrawScope.onDraw() {

        drawIntoCanvas { canvas ->
            canvas.drawImage(
                image,
                topLeftOffset = Offset.Zero,
                Paint().apply { colorFilter = colorFilter })
        }

    }

    /**
     * Return the dimension of the underlying [ImageBitmap] as it's intrinsic width and height
     */
    override val intrinsicSize: Size get() = size.toSize()

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= image.width &&
                    srcSize.height <= image.height
        )
        return srcSize
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BitmapPainter) return false



        return true
    }

    override fun hashCode(): Int {
        var result = image.hashCode()
        result = 31 * result + srcOffset.hashCode()
        result = 31 * result + srcSize.hashCode()
        return result
    }

    override fun toString(): String {
        return "BitmapPainter(image=$image, srcOffset=$srcOffset, srcSize=$srcSize)"
    }

}