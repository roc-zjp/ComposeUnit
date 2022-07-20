package com.zjp.compose_unit.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.zjp.compose_unit.R
import java.util.*

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
)


val local: FontFamily = FontFamily.Default
val IndieFlower: FontFamily = FontFamily(Font(R.font.indieflower_regular))
val BalooBhai2: FontFamily = FontFamily(Font(R.font.baloobhai2_regular))
val Inconsolata: FontFamily = FontFamily(Font(R.font.inconsolata_regular))
val Neucha: FontFamily = FontFamily(Font(R.font.neucha_regular))
val ComicNeue: FontFamily = FontFamily(Font(R.font.comicneue_regular))
val CHOPS: FontFamily = FontFamily(Font(R.font.chops))


val fontMap = mapOf<String, FontFamily>(
    "local" to local,
    "Inconsolata" to Inconsolata,
    "IndieFlower" to IndieFlower,
    "BalooBhai2" to BalooBhai2,
    "Neucha" to Neucha,
    "ComicNeue" to ComicNeue,
    "CHOPS" to CHOPS,
)


val LocalFont = compositionLocalOf { "local" }
