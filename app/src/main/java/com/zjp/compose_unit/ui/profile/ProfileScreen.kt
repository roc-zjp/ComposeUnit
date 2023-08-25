package com.zjp.compose_unit.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(), navigateToRoute: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        viewModel.message.observe(lifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
    ProfileView(
        currentAppVersionName = viewModel.currentAppVersionName,
        currentDatabaseVersion = viewModel.currentDatabaseVersion,
        checkAppUpdate = { viewModel.checkAppUpdate() },
        checkDatabaseUpdate = { viewModel.checkDatabaseUpdate() },
        navigateToRoute = navigateToRoute
    )

}


@Composable
fun ProfileView(
    currentAppVersionName: String = "",
    currentDatabaseVersion: String = "",
    checkAppUpdate: () -> Unit = {},
    checkDatabaseUpdate: () -> Unit = {},
    navigateToRoute: (String) -> Unit = {}
) {
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
            painter = painterResource(id = R.drawable.kobe),
            contentDescription = "kobe",
            modifier = Modifier
                .padding(top = 160.dp, start = 25.dp)
                .width(80.dp)
                .height(80.dp)
                .clip(CircleShape)
        )

    }) { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {
            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())

            ) {
                Text(
                    text = "天涯浪子",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                )
                ListItem(headlineContent = { Text(text = "应用设置") }, leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.setting),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }, trailingContent = {}, modifier = Modifier.clickable {
                    navigateToRoute(Screen.AppSetting.route)
                })


                ListItem(headlineContent = { Text(text = "关于应用") }, leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.about_app),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }, trailingContent = {}, modifier = Modifier.clickable {
                    navigateToRoute(Screen.AboutApp.route)
                })


                ListItem(headlineContent = { Text(text = "关于我") }, leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.about_me),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }, trailingContent = {}, modifier = Modifier.clickable {
                    navigateToRoute(Screen.AboutApp.route)
                })






                Divider()

                ListItem(headlineContent = { Text(text = "APP版本") },
                    supportingContent = {
                        Text(text = "当前已是最新版本")
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check_update),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }, trailingContent = {
                        Box(
                            modifier = Modifier.height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "V${currentAppVersionName}")
                        }
                    }, modifier = Modifier.clickable {
                        checkAppUpdate()
                    })

                ListItem(headlineContent = { Text(text = "数据库版本") },
                    supportingContent = {
                        Text(text = "当前已是最新版本")
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.database),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }, trailingContent = {
                        Box(
                            modifier = Modifier.height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "V${currentDatabaseVersion}")
                        }
                    }, modifier = Modifier.clickable {
                        checkDatabaseUpdate()
                    })
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileScreenPre() {
    ProfileView()
}