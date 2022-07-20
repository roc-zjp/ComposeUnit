package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.Screen


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navigateToRoute: (String) -> Unit = {}) {
    Scaffold(
        topBar = {
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
                    .padding(top = 175.dp, start = 25.dp)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)

            )

        }
    ) { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "天涯浪子",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                )
                ListItem(icon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }, modifier = Modifier.clickable {
                    navigateToRoute(Screen.AppSetting.route)
                }) {
                    Text(text = "应用设置")
                }
                ListItem(icon = {
                    Icon(
                        Icons.Outlined.Create,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }) {
                    Text(text = "关于应用")
                }
                ListItem(icon = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }) {
                    Text(text = "关于我")
                }
                Divider()
                ListItem(icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.check_update),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }) {
                    Text(text = "检查APP版本")
                }
                ListItem(icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.database),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary
                    )
                }) {
                    Text(text = "检查数据库版本")
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPre() {
    ProfileScreen() {}
}