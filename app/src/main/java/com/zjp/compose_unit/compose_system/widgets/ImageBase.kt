package com.zjp.compose_unit.compose_system.widgets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.zjp.compose_unit.R
import com.zjp.compose_unit.ui.custom.WrapLayout

/**
 * 从资源文件和网络获取图片
 * 【painter】：可画东西的抽象【Painter】
 */
@Composable
fun ImageBase() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.jetpack_compose),
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
    }
}


/**
 * 图片缩放类型
 */
@Composable
fun ImageContentScaleType() {
    var scaleType = listOf<ContentScale>(
        ContentScale.Fit,
        ContentScale.Crop,
        ContentScale.FillBounds,
        ContentScale.None,
        ContentScale.FillHeight,
        ContentScale.FillWidth,
        ContentScale.Inside
    )
    var scaleTypeName = listOf<String>(
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
    var qualityType = listOf<FilterQuality>(
        FilterQuality.None,
        FilterQuality.Low,
        FilterQuality.Medium,
        FilterQuality.High
    )
    var qualityTypeName = listOf<String>(
        "None",
        "Low",
        "Medium",
        "High",
    )
    var context = LocalContext.current
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
                        R.drawable.cart
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
    var modes = listOf<BlendMode>(
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
    WrapLayout(modifier = Modifier) {
        modes.forEach {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cart),
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


@Preview(showSystemUi = true)
@Composable
fun ImageDefaultPre() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageBase()
        ImageContentScaleType()
        ImageQuality()
        ImageBlendMode()
    }
}