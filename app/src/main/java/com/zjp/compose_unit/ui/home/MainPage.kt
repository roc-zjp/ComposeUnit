package com.zjp.compose_unit.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.R
import com.zjp.compose_unit.route.BottomNavigationScreens
import com.zjp.compose_unit.route.Screen

import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.core_database.model.Compose


@Composable
fun MainView(
    onClick: (compose: Compose) -> Unit,
    toDeveloper: () -> Unit = {},
) {
    var currentTableName by remember {
        mutableStateOf(BottomNavigationScreens.Composes.tableName)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Composes Unit") },
                actions = {
                    IconButton(onClick = {
                        toDeveloper()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.debug),
                            contentDescription = "Home",
                        )
                    }

                }
            )

        },
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape) {

                BottomNavigationItem(icon = {
                    Icon(
                        painter = painterResource(id = BottomNavigationScreens.Composes.iconResId),
                        contentDescription = null
                    )
                },
                    label = { Text(BottomNavigationScreens.Composes.tableName) },
                    selected = currentTableName == BottomNavigationScreens.Composes.tableName,
                    onClick = {
                        currentTableName = BottomNavigationScreens.Composes.tableName
                    })

                Spacer(modifier = Modifier.width(100.dp))

                BottomNavigationItem(icon = {
                    Icon(
                        painter = painterResource(id = BottomNavigationScreens.Profile.iconResId),
                        contentDescription = null
                    )
                },
                    label = { Text(BottomNavigationScreens.Profile.tableName) },
                    selected = currentTableName == BottomNavigationScreens.Profile.tableName,
                    onClick = {
                        currentTableName = BottomNavigationScreens.Profile.tableName
                    })

            }
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                when (currentTableName) {
                    BottomNavigationScreens.Profile.tableName -> ProfileScreen()
                    BottomNavigationScreens.Composes.tableName -> ComposesScreen() { compose ->
                        onClick(compose)
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(50),
                backgroundColor = Color.Magenta
            ) {
                Icon(Icons.Filled.Add, tint = Color.White, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    )
}

@Preview(name = "Dark Mode")
@Composable
fun ComposeItemPreview() {
    Compose_unitTheme() {
        var navController = rememberNavController()
        MainView(onClick = {
            navController.navigate(Screen.ComposeDetailScreen.createComposeDetailRoute(it))
        })
    }
}