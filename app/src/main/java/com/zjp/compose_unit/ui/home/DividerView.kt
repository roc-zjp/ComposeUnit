package com.zjp.compose_unit.common.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.ui.theme.Compose_unitTheme

@Composable
fun DividerView(title: String) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .width(10.dp)
                    .height(10.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    color = Color.Red,
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = canvasWidth / 2
                )
            }
            Spacer(modifier = Modifier.padding(start = 10.dp))

            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.Black, fontSize = 18.sp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ComposeItemPreview() {
    Compose_unitTheme(darkTheme = false) {
        DividerView("标题")
    }
}