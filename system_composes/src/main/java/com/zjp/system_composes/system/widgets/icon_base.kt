package com.zjp.system_composes.system.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.zjp.system_composes.R

/**
 *【painter】：所有可画对象的抽象【Painter】
 *【contentDescription】：描述【String】
 *【tint】：染色颜色【Color】
 *【bitmap】：位图【ImageBitmap】
 */
@Composable
fun IconBase() {
    Row {
        Icon(
            painter = painterResource(id = com.zjp.common.R.drawable.cart),
            contentDescription = "cart",
            tint = Color.Red
        )
        Icon(Icons.Default.Menu, contentDescription = "Menu")
        Icon(
            bitmap = ImageBitmap.imageResource(id = com.zjp.common.R.drawable.cart),
            contentDescription = "Menu"
        )

    }
}

@Preview
@Composable
fun IconPreview() {
    IconBase()
}