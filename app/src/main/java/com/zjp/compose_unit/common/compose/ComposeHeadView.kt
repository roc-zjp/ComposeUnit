package com.zjp.compose_unit.common.compose


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.zjp.compose_unit.R

import com.zjp.compose_unit.ui.theme.Compose_unitTheme

@Composable
fun ComposeHeadView(name: String, description: String) {
    Log.d("dp", 100.dp.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)

        ) {
            Text(
                text = name, style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff4699FB), fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = description, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
        Image(
            painter = painterResource(id = R.drawable.caver),
            contentDescription = null,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .width(100.dp)
                .height(66.dp)
        )
    }
}


@Preview(name = "dark", showBackground = true)
@Composable
fun ComposeHeadPreview() {
    Compose_unitTheme(darkTheme = false) {
        ComposeHeadView("标题", "详情")
    }
}