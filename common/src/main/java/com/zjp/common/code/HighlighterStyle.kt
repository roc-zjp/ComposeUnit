package com.zjp.common.code

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle

val lightColor = listOf<Color>(
    Color(0xFF000000), //基础
    Color(0xFF00b0e8), //数字
    Color(0xFF9E9E9E), //注释
    Color(0xFF9C27B0), //关键
    Color(0xFF43A047), //字符串
    Color(0xFF000000), //标点符号
    Color(0xFF3D62F5), //类名
    Color(0xFF795548), //常量
    Color(0xffF6F8FA)
)

val darkColor = listOf<Color>(
    Color(0xFFFFFFFF), //基础
    Color(0xFFDF935F), //数字
    Color(0xFF9E9E9E), //注释
    Color(0xFF80CBC4), //关键字
    Color(0xFFB9CA4A), //字符串
    Color(0xFFFFFFFF), //标点符号
    Color(0xFF7AA6DA), //类名
    Color(0xFF795548), //常量
    Color(0xFF1D1F21),
)

val gitHub = listOf<Color>(
    Color(0xFF333333), //基础
    Color(0xFF008081), //数字
    Color(0xFF9D9D8D), //注释
    Color(0xFF009999), //关键字
    Color(0xFFDD1045), //字符串
    Color(0xFF333333), //标点符号
    Color(0xFF6F42C1), //类名
    Color(0xFF795548), //常量
    Color(0xFFF8F8F8), //背景
)


val zenburn = listOf<Color>(
    Color(0xFFDCDCDC), //普通字
    Color(0xFF87C5C8), //数字
    Color(0xFF8F8F8F), //注释
    Color(0xFFE4CEAB), //关键字
    Color(0xFFCC9493), //字符串
    Color(0xFFDCDCDC), //标点符号
    Color(0xFFEFEF90), //类名
    Color(0xFFEF5350), //常量
    Color(0xFF3F3F3F), //背景
)

val mf = listOf<Color>(
    Color(0xFF707D95), //基础
    Color(0xFF6897BB), //数字
    Color(0xFF629755), //注释
    Color(0xFFCC7832), //关键
    Color(0xFFF14E9F), //字符串
    Color(0xFFFFBB33), //标点符号
    Color(0xFF66CCFF), //类名
    Color(0xFF9876AA), //常量
    Color(0xFF2B2B2B) //背景
)

val solarized = listOf<Color>(
    Color(0xFF657B83),  // 普通字
    Color(0xFFD33682),  // 数字
    Color(0xFF93A1A1),  // 注释
    Color(0xFF859900),  // 关键字
    Color(0xFF2AA198),  // 字符串
    Color(0xFF859900),  // 标点符号
    Color(0xFF268BD2),  // 类名
    Color(0xFF268BD2),  //常量
    Color(0xFFDDD6C1),  // 背景
)

class HighlighterStyle(
    var baseStyle: TextStyle? = null,
    var numberStyle: TextStyle? = null,
    var commentStyle: TextStyle? = null,
    var keywordStyle: TextStyle? = null,
    var stringStyle: TextStyle? = null,
    var punctuationStyle: TextStyle? = null,
    var classStyle: TextStyle? = null,
    var constantStyle: TextStyle? = null,
    var backgroundColor: Color? = null
)

fun HighlighterStyle.fromColors(colors: List<Color>): HighlighterStyle {
    return HighlighterStyle(
        baseStyle = TextStyle(color = colors[0]),
        numberStyle = TextStyle(color = colors[1]),
        commentStyle = TextStyle(color = colors[2], fontStyle = FontStyle.Italic),
        keywordStyle = TextStyle(color = colors[3]),
        stringStyle = TextStyle(color = colors[4]),
        punctuationStyle = TextStyle(color = colors[5]),
        classStyle = TextStyle(color = colors[6]),
        constantStyle = TextStyle(color = colors[7]),
        backgroundColor = colors[8]
    )
}