package com.zjp.compose_unit.ui.home


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
import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.core_database.model.Compose

@Composable
fun ComposeHeadView(compose: Compose?) {

    if (compose == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
    } else {
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
                    text = compose.name, style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff4699FB), fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(text = compose.info, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
            Image(
                painter = painterResource(id = com.zjp.system_composes.R.drawable.caver),
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
}


@Preview(name = "dark", showBackground = true)
@Composable
fun ComposeHeadPreview() {
    Compose_unitTheme(darkTheme = false) {
        ComposeHeadView(null)
    }
}