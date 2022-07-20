package com.zjp.compose_unit.ui.developer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.apkfuns.logutils.LogUtils
import com.zjp.compose_unit.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.security.Provider


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeveloperScreen(
    onBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
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
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                CompositionLocal()
                Spacer(modifier = Modifier.height(50.dp))
                StaticCompositionLocal()
            }
        }
    )
}


@Composable
fun ProviderBase() {
    Column {
        Text("Uses MaterialTheme's provided alpha")
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text("Medium value provided for LocalContentAlpha")
            Text("This Text also uses the medium value")
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                DescendantExample()
            }
        }
    }
}


@Composable
fun DescendantExample() {
    // CompositionLocalProviders also work across composable functions

//    val resources = LocalContext.current.resources
//    val fruitText = remember(resources, fruitSize) {
//        resources.getQuantityString(R.plurals.fruit_title, fruitSize)
//    }
    Text("This Text uses the disabled alpha now")
}


data class Elevations(val card: Dp = 0.dp, val default: Dp = 0.dp)

// Define a CompositionLocal global object with a default
// This instance can be accessed by all composables in the app
val LocalElevations = compositionLocalOf { Elevations() }

@Composable
fun CustomProvider() {
    val elevations = if (isSystemInDarkTheme()) {
        Elevations(card = 1.dp, default = 1.dp)
    } else {
        Elevations(card = 0.dp, default = 0.dp)
    }
    CompositionLocalProvider(LocalElevations provides elevations) {
        // ... Content goes here ...
        // This part of Composition will see the `elevations` instance
        // when accessing LocalElevations.current

        Card(elevation = LocalElevations.current.card) {
            // Content

        }
    }
}


@Composable
fun CompositionLocal() {
    val elevations =
        Elevations(card = 1.dp, default = 1.dp)

    CompositionLocalProvider(LocalElevations provides elevations) {

        Card(elevation = LocalElevations.current.card) {
            LogUtils.d("重组")
            Button(onClick = {
                LocalElevations.provides(Elevations(card = 0.dp, default = 0.dp))
            }) {
                Text(text = "Change Elevation")
            }
        }
    }
}

val StaticLocalElevations = compositionLocalOf { Elevations() }

@Composable
fun StaticCompositionLocal() {
    val elevations =
        Elevations(card = 1.dp, default = 1.dp)

    CompositionLocalProvider(StaticLocalElevations provides elevations) {
        // ... Content goes here ...
        // This part of Composition will see the `elevations` instance
        // when accessing LocalElevations.current

        Card(elevation = StaticLocalElevations.current.card) {
            LogUtils.d("Static 重组")
            Button(onClick = {
                StaticLocalElevations.provides(Elevations(card = 10.dp, default = 10.dp))
            }) {
                Text(text = "Static Change Elevation")
            }
        }

    }
}



