package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apkfuns.logutils.LogUtils
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.zjp.compose_unit.R
import com.zjp.compose_unit.common.LocalThemeColor
import com.zjp.compose_unit.ui.detail.NodeTitle
import com.zjp.compose_unit.ui.theme.IndieFlower

@Composable
fun AboutAppScreen(goBack: () -> Unit = {}) {
    Scaffold(topBar = {
        ProvideWindowInsets() {
            Image(
                painter = painterResource(id = com.zjp.system_composes.R.drawable.caver),
                contentScale = ContentScale.Crop,
                contentDescription = "Header",
                modifier = Modifier
                    .height(
                        200.dp
                    )
                    .fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.kobe), contentDescription = "kobe",
                modifier = Modifier
                    .padding(top = 150.dp, start = 25.dp)
                    .width(100.dp)
                    .height(100.dp)
                    .clip(CircleShape)
            )

            LogUtils.d("top=${LocalWindowInsets.current.statusBars.top},bottom=${LocalWindowInsets.current.statusBars.bottom}")
            IconButton(onClick = {
                goBack()
            }, modifier = Modifier.statusBarsPadding()) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = Color.White
                )
            }
        }
    }) { paddingValue ->

        val color = LocalThemeColor.current
        Column(
            Modifier
                .padding(paddingValue)
                .padding(end = 10.dp, start = 10.dp)
        ) {
            Text(
                text = "Compose Unit", fontFamily = IndieFlower, fontSize = 30.sp
            )
            NodeTitle(title = "项目简介", showLeft = false, modifier = Modifier)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(color.copy(alpha = 0.5f))
            ) {
                Text(text = "项目简介", modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            NodeTitle(title = "项目简介", showLeft = false, modifier = Modifier.padding(top = 10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()

                    .clip(RoundedCornerShape(5.dp))
                    .background(color.copy(alpha = 0.5f))
            ) {
                Text(text = "项目简介", modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun AboutAppPreview() {
    AboutAppScreen()
}