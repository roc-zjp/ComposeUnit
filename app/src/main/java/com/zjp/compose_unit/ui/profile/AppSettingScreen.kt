package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppSettingScreen(navigateToRoute: (String) -> Unit = {}, goBack: () -> Unit = {}) {
    Scaffold(topBar = {
        UnitTopAppBar(title = { Text(text = "应用设置") }, navigationIcon = {
            IconButton(onClick = {
                goBack()
            }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            }
        })
    }) {
        Column(Modifier.padding(it)) {
            ListItem(icon = {
                Icon(
                    painter = painterResource(id = R.drawable.theme_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }, trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colors.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.ThemeSetting.route)
            }) {
                Text(text = "主题设置")
            }
            Divider()
            ListItem(icon = {
                Icon(
                    painter = painterResource(id = R.drawable.font_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }, trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colors.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.FontSetting.route)
            }) {
                Text(text = "字体设置")
            }
            Divider()
            ListItem(icon = {
                Icon(
                    painter = painterResource(id = R.drawable.item_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }, trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colors.primary
                )
            }) {
                Text(text = "item样式")
            }
            Divider()
            ListItem(icon = {
                Icon(
                    painter = painterResource(id = R.drawable.app_version),
                    contentDescription = "",
                    tint = MaterialTheme.colors.primary
                )
            }, trailing = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colors.primary
                )
            }) {
                Text(text = "版本信息")
            }
            Divider()
        }
    }
}