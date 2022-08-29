package com.zjp.compose_unit.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.common.LocalFont
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.CustomIndicator
import com.zjp.common.compose.SystemBroadcastReceiver
import com.zjp.compose_unit.R
import com.zjp.compose_unit.common.colorBlue
import com.zjp.compose_unit.common.color_change_broadcast_action
import com.zjp.compose_unit.common.compose.UnitBottomAppBar
import com.zjp.compose_unit.common.font_change_broadcast_action
import com.zjp.compose_unit.route.HomeSections
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.route.unitNavGraph
import com.zjp.compose_unit.ui.theme.Compose_unitTheme
import com.zjp.compose_unit.ui.theme.fontMap
import com.zjp.compose_unit.ui.theme.local
import kotlinx.coroutines.launch

@Composable
fun App() {

    val navController = rememberNavController()
    var context = LocalContext.current
    var scope = rememberCoroutineScope()


    var themeColor by remember {
        mutableStateOf(getThemeColor(context))
    }

    var currentFont by remember {
        mutableStateOf(local)
    }


    SystemBroadcastReceiver(systemAction = color_change_broadcast_action) { intent ->
        val colorStr = intent?.getIntExtra("color", 0xFF2196F3.toInt()) ?: 0xFF2196F3.toInt()
        themeColor = Color(colorStr)
        scope.launch { saveThemeColor(context, themeColor) }
    }
    SystemBroadcastReceiver(systemAction = font_change_broadcast_action) { intent ->
        val fontStr = intent?.getStringExtra("font") ?: "local"
        val font = fontMap[fontStr] ?: local
        currentFont = font
        scope.launch { saveFont(context, font) }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: Screen.Splash.route
    val routes = remember { HomeSections.values().map { it.route } }


    CompositionLocalProvider(
        LocalThemeColor provides themeColor,
        LocalFont provides currentFont,
    ) {
        Compose_unitTheme(primary = themeColor, font = currentFont) {
            Scaffold() { innerPaddingModifier ->
                Box(modifier = Modifier.padding(innerPaddingModifier)) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier

                    ) {
                        unitNavGraph(navController)
                    }
                    if (currentRoute in routes) {
                        UnitBottomAppBar(
                            modifier = Modifier.align(Alignment.BottomStart),
                            onTabChange = { section ->
                                navController.navigate(section.route) {
                                    popUpTo(HomeSections.COMPOSE.route) {
                                        inclusive = false
                                    }
                                    launchSingleTop

                                }
                            }, onSearchClick = {
                                navController.navigate(Screen.Search.route)
                            })
                    }
                }
            }
        }
    }
}


private fun saveThemeColor(context: Context, color: Color) {
    context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).edit()
        .putInt("themeColor", color.toArgb()).commit()

}

private fun getThemeColor(context: Context): Color {
    var colorInt =
        context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).getInt(
            "themeColor",
            colorBlue.toArgb()
        )
    return Color(colorInt)
}


private fun saveFont(context: Context, font: FontFamily) {
    val index = fontMap.values.indexOf(font)
    context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE).edit()
        .putString("font", fontMap.keys.elementAt(index)).commit()
}

private fun getFont(context: Context): FontFamily {
    val fontStr = context.getSharedPreferences("custom_setting", Context.MODE_PRIVATE)
        .getString("font", "") ?: "local"

    return fontMap[fontStr] ?: local
}


@Preview
@Composable
fun BottomPreview() {
    UnitBottomAppBar()
}






