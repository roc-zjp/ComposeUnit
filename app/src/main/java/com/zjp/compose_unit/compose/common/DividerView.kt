package com.zjp.compose_unit.compose.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.compose_unit.R

@Composable
fun DividerView(title: String) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.dot),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .size(width = 10.dp, height = 10.dp)
                    .clip(CircleShape), // 这是为圆形裁剪
                contentScale = ContentScale.FillBounds, // 等价于ImageView的fitXY
                alignment = Alignment.Center, // 居中显示
            )
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