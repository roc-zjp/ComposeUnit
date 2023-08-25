package com.zjp.compose_unit.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.Screen


@Composable
fun AppSettingPage(navigateToRoute: (String) -> Unit = {}, goBack: () -> Unit = {}) {
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
            ListItem(headlineContent = { Text(text = "主题设置") }, leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.theme_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.ThemeSetting.route)
            })

            Divider()
            ListItem(headlineContent = { Text(text = "字体设置") }, leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.font_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.ThemeSetting.route)
            })

            Divider()

            ListItem(headlineContent = { Text(text = "item样式") }, leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.item_icon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.ItemSetting.route)
            })

            Divider()

            ListItem(headlineContent = { Text(text = "版本信息") }, leadingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.app_version),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, trailingContent = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_bold),
                    contentDescription = "theme",
                    tint = MaterialTheme.colorScheme.primary
                )
            }, modifier = Modifier.clickable {
                navigateToRoute(Screen.ItemSetting.route)
            })


            Divider()
        }
    }
}