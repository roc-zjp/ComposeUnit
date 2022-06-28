package com.zjp.compose_unit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.zjp.compose_unit.compose_system.buttons.ButtonDrawerBase

import com.zjp.compose_unit.compose_system.buttons.ButtonPre

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeveloperScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "开发者页面") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        content = {
            ButtonPre()
        }
    )
}