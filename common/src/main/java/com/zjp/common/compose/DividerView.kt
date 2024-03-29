package com.zjp.common.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.common.LocalThemeColor
import com.zjp.common.R

@Composable
fun NodeTitle(
    title: String,
    folded: Boolean = false,
    showLeft: Boolean = true,
    modifier: Modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp),
    toggleFold: () -> Unit = {}
) {
    val color = LocalThemeColor.current
    Column {
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Canvas(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    color = color,
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = canvasWidth / 2
                )
            }
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.Black, fontSize = 18.sp),
                modifier = Modifier.weight(1f)
            )

            if (showLeft) {
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Icon(Icons.Sharp.Share, contentDescription = "share", tint = color)
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Icon(
                    painter = painterResource(id = if (folded) R.drawable.ic_baseline_unfold_more_24 else R.drawable.ic_baseline_unfold_less_24),
                    contentDescription = "less",
                    tint = color,
                    modifier = Modifier
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }) { toggleFold() }
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ComposeItemPreview() {

    NodeTitle("标题标题标题标题标题标题标题标题标题标题标题标题标题") {

    }

}