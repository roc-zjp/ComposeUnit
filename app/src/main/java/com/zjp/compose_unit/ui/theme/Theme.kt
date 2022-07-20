package com.zjp.compose_unit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.R

private val DarkColorPalette = darkColors(
    primaryVariant = md_theme_dark_primary,
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)

private val LightColorPalette = lightColors(
    primaryVariant = md_theme_light_primary,
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,

    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,

    error = md_theme_light_error,
    onError = md_theme_light_onError,

    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)


@Composable
fun Compose_unitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    primary: Color = Color.Blue,
    font: FontFamily = local,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette.copy(primary = primary)
    } else {
        LightColorPalette.copy(primary = primary)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(
            body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            defaultFontFamily = font
        ),
        shapes = Shapes,
        content = content
    )
}


val Rubik = FontFamily(
    Font(R.font.chops, FontWeight.Light),
    Font(R.font.neucha_regular, FontWeight.Normal),
    Font(R.font.inconsolata_regular, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.indieflower_regular, FontWeight.Medium),
    Font(R.font.comicneue_regular, FontWeight.Bold)
)

val MyTypography = Typography(
    h1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    body1 = TextStyle(
        fontFamily = Rubik,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    )
    /*...*/
)


val Yellow200 = Color(0xffffeb46)
val Blue200 = Color(0xff91a4fc)
// ...

//val DarkColors = darkColors(
//    primary = Color(0xff4699FB),
//    secondary = Blue200,
//    // ...
//)
// val LightColors = lightColors(
//    primary = Color(0xff4699FB),
//    primaryVariant = Yellow400,
//    secondary = Blue700,
//    // ...
//)


val tabColors = arrayListOf<Color>(
    Color(0xff44D1FD),
    Color(0xffFD4F43),
    Color(0xffB375FF),
    Color(0xFF4CAF50),
    Color(0xFFFF9800),
    Color(0xFF00F1F1),
    Color(0xFFDBD83F),
)

val fontFamilySupport = arrayListOf<String>(
    "local",
    "ComicNeue",
    "IndieFlower",
    "BalooBhai2",
    "Inconsolata",
    "Neucha"
)

val themeColorSupport = mapOf<Color, String>(
    Color(244, 67, 54) to "毁灭之红",
    Color(255, 152, 0) to "愤怒之橙",
    Color(255, 235, 59) to "警告之黄",
    Color(76, 175, 80) to "伪装之绿",
    Color(33, 150, 243) to "冷漠之蓝",
    Color(63, 81, 181) to "无限之靛",
    Color(156, 39, 176) to "神秘之紫",
    Color(45, 45, 45) to "归宿之黑",
)

