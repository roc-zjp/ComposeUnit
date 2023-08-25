package com.zjp.common.compose


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@Composable
fun ComposeHeadView(name: String?, info: String?) {
    if (name == null || info == null) {
        LoadingHeader()
    } else {
        InfoHeader(name, info)
    }
}

@Composable
fun InfoHeader(name: String, info: String) {
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
            Text(text = info, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
        Image(
            painter = painterResource(id = com.zjp.common.R.drawable.caver),
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

// 改变Alpha值，模拟加载中动画
@Composable
fun LoadingHeader() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.7f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )
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
                text = "", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff4699FB), fontSize = 20.sp
                ),
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = alpha))
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "",
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = alpha))
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .width(100.dp)
                .height(66.dp)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}

@Preview(name = "dark", showBackground = true)
@Composable
fun ComposeHeadPreview() {

    ComposeHeadView("name", "info")

}