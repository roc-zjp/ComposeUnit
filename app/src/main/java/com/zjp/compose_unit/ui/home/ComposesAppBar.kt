package com.zjp.compose_unit.ui.home


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjp.compose_unit.common.Const
import com.zjp.compose_unit.common.palettes

@Composable
fun ComposesAppBar(selectIndex: Int, onItemSelected: (index: Int) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.01f))
            .height(100.dp)
            .shadow(elevation = 0.dp)
    ) {
        palettes.forEachIndexed { index, value ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent)
                    .shadow(elevation = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val boxSize by animateDpAsState(
                    targetValue = if (selectIndex == index) 100.dp else 75.dp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(boxSize)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        )
                        .clickable {
                            onItemSelected(index)
                        }
                        .background(Const.tabColors[index])
                ) {
                    Text(
                        text = value.substring(0, 3),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 5.dp)
                    )
                }
                if (selectIndex != index) {
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .width(20.dp)
                            .height(20.dp)
                            .clip(CircleShape)
                            .background(Const.tabColors[index])
                    )
                }
            }
        }
    }

}

@Preview(showSystemUi = true, backgroundColor = 0xff0000)
@Composable
fun ComposesAppBarPre() {
    ComposesAppBar(0) {}
}