package com.zjp.system_composes.system.widgets

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.zjp.common.compose.WrapLayout
import kotlin.math.roundToInt


/**
 * 从资源文件和网络获取图片
 * 【painter】：可画东西的抽象【Painter】
 */
@Composable
fun ImageBase() {
    Row {
        Image(
            painter = painterResource(id = com.zjp.common.R.drawable.jetpack_compose),
            contentDescription = "图标",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            colorFilter = ColorFilter.tint(Color.Gray, BlendMode.Color)
        )
        Image(
            painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2014/07/31/22/50/photographer-407068_1280.jpg"),
            contentDescription = "图标",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )
        Image(
            painter = painterAssets("jetpack_compose.png"),
            contentDescription = "图标",
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
        )
    }
}


/**
 * 图片缩放类型
 */
@Composable
fun ImageContentScaleType() {
    val scaleType = listOf<ContentScale>(
        ContentScale.Fit,
        ContentScale.Crop,
        ContentScale.FillBounds,
        ContentScale.None,
        ContentScale.FillHeight,
        ContentScale.FillWidth,
        ContentScale.Inside
    )
    val scaleTypeName = listOf<String>(
        "Fit",
        "Crop",
        "FillBounds",
        "None",
        "FillHeight",
        "FillWidth",
        "Inside"
    )
    WrapLayout {
        scaleType.forEachIndexed { index, value ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("https://cdn.pixabay.com/photo/2014/07/31/22/50/photographer-407068_1280.jpg"),
                    contentDescription = "图标",
                    contentScale = value,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .background(Color.Red)
                )
                Text(text = scaleTypeName[index], modifier = Modifier)
            }
        }

    }
}


@Composable
fun ImageQuality() {
    val qualityType = listOf<FilterQuality>(
        FilterQuality.None,
        FilterQuality.Low,
        FilterQuality.Medium,
        FilterQuality.High
    )
    val qualityTypeName = listOf<String>(
        "None",
        "Low",
        "Medium",
        "High",
    )
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {

        qualityType.forEachIndexed { index, value ->
            Spacer(modifier = Modifier.width(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        context.resources,
                        com.zjp.common.R.drawable.cart
                    ),
                    contentDescription = "图标",
                    filterQuality = value,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)

                )
                Text(text = qualityTypeName[index], modifier = Modifier)
            }
        }
    }
}

@Composable
fun ImageBlendMode() {
    val modes = listOf<BlendMode>(
        BlendMode.Color,
        BlendMode.Clear,
        BlendMode.SrcIn,
        BlendMode.ColorBurn,
        BlendMode.Src,
        BlendMode.ColorDodge,
        BlendMode.Darken,
        BlendMode.Difference,
        BlendMode.Dst,
        BlendMode.DstAtop,
        BlendMode.DstIn,
        BlendMode.DstOut,
        BlendMode.DstOver,
        BlendMode.Exclusion,
        BlendMode.Hardlight,
        BlendMode.Hue,
        BlendMode.Lighten,
        BlendMode.Luminosity,
        BlendMode.Modulate,
        BlendMode.Multiply,
        BlendMode.Overlay,
        BlendMode.Plus,
        BlendMode.Saturation,
        BlendMode.Screen,
        BlendMode.Softlight,
        BlendMode.SrcAtop,
        BlendMode.SrcOut,
        BlendMode.SrcOver,
        BlendMode.Xor
    )
    WrapLayout(modifier = Modifier.requiredHeightIn(20.dp, 10000.dp)) {
        modes.forEach {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = com.zjp.common.R.drawable.cart),
                    contentDescription = "图标",
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    colorFilter = ColorFilter.tint(Color.Red, it)
                )
                Text(text = it.toString())
            }
        }
    }
}

@Composable
fun CornerImage() {
    Image(
        painter = painterResource(com.zjp.common.R.drawable.caver),
        contentDescription = "图标",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .clip(CircleShape)
            .border(2.dp, color = Color.Red, CircleShape)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableBase() {
    Text(text = "SwipeableBase")
//    val width = 350.dp
//    val squareSize = 50.dp
//
//
//
//    val swipeableState = rememberSwipeableState(initialValue = "A")
//    val sizePx = with(LocalDensity.current) { (width - squareSize).toPx() }
//    val anchors = mapOf(0f to "A", sizePx / 2 to "B", sizePx to "C")
//
//    Box(
//        modifier = Modifier
//            .width(width)
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.5f) },
//                orientation = Orientation.Horizontal
//            )
//            .background(Color.Black)
//    ) {
//        Box(
//            Modifier
//                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
//                .size(squareSize)
//                .background(Color.Red),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(swipeableState.currentValue, color = Color.White, fontSize = 24.sp)
//        }
//    }
}


@Preview(showSystemUi = true)
@Composable
fun ImageDefaultPre() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CornerImage()
        ImageBase()
        ImageContentScaleType()
        ImageQuality()
        ImageBlendMode()
        SwipeableBase()
    }
}


@Composable
fun painterAssets(name: String): Painter {
    val context = LocalContext.current

    val imageBitmap = remember(name, context.theme) {
        loadImageBitmapAssets(context, name)
    }
    return BitmapPainter(imageBitmap)
}

private fun loadImageBitmapAssets(context: Context, name: String): ImageBitmap {
    try {
        return ImageBitmap.assetsImage(context, name)
    } catch (throwable: Throwable) {
        throw IllegalArgumentException()
    }
}

fun ImageBitmap.Companion.assetsImage(context: Context, name: String): ImageBitmap {
    val bitmap = context.assets
        .open(name)
        .use(BitmapFactory::decodeStream)

    return bitmap.asImageBitmap()
}