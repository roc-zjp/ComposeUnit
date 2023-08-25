package com.zjp.compose_unit.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.zjp.compose_unit.R


@Composable
fun AboutMeScreen(goBack: () -> Unit = {}) {
    Scaffold(topBar = {

        Image(
            painter = painterResource(id = com.zjp.common.R.drawable.caver),
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

        IconButton(onClick = {
            goBack()
        }, modifier = Modifier.statusBarsPadding()) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }

    }) { paddingValue ->
        val context = LocalContext.current
        Column(
            modifier = Modifier.padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier.padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(text = "天涯浪子", fontSize = 24.sp)
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(
                        enabled = true,
                        role = null,
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        val uri: Uri = Uri.parse("https://juejin.cn/user/272334611820622")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.juejin),
                        contentDescription = "juejin"
                    )
                    Text(text = "掘金")
                }
                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable(
                        enabled = true,
                        role = null,
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                        val uri: Uri = Uri.parse("https://github.com/roc-zjp")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)

                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "github"
                    )
                    Text(text = "Github")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.wechat),
                contentDescription = "wechat",
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(text = "我的微信", modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Preview
@Composable
fun Preview() {
    AboutMeScreen()
}