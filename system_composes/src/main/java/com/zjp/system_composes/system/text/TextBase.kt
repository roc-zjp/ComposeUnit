package com.zjp.system_composes.system.text

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * 常用属性
 */
@Composable
fun TextCommon() {
    Text(
        "Hello World",
        color = Color.Blue,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .width(150.dp)
            .background(Color(0xff44D1FD)),
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun TextMaxLine() {
    Text(
        "Hello World".repeat(50),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = Color.Blue,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .width(150.dp)
            .background(Color(0xff44D1FD)),
        fontFamily = FontFamily.SansSerif
    )
}


/**
 *富文本显示
 */
@Composable
fun RichText() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("H")
            }
            append("ello ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                append("W")
            }
            append("orld")
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("Hello\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                ) {
                    append("World\n")
                }
                append("Compose")
            }
        }, modifier = Modifier.padding(start = 10.dp).background(Color(0xff44D1FD))
    )
}

/**
 * 部分文字可选
 */
@Composable
fun PartiallySelectableText() {
    SelectionContainer {
        Column(modifier = Modifier.padding(start = 10.dp).background(Color(0xff44D1FD))) {
            Text("This text is selectable")
            Text("This one too")
            Text("This one as well")
            DisableSelection {
                Text("But not this one")
                Text("Neither this one")
            }
            Text("But again, you can select this one")
            Text("And this one too")
        }
    }
}

/**
 * 可点击文字
 */
@Composable
fun AnnotatedClickableText() {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        append("Click ")
        pushStringAnnotation(tag = "URL", annotation = "https://developer.android.com")
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
            append("here")
        }
        pop()
    }
    ClickableText(
        modifier = Modifier.padding(start = 10.dp).background(Color(0xff44D1FD)),
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                    val uri = Uri.parse(annotation.item)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPre() {
    Column {
        TextCommon()
        TextMaxLine()
        RichText()
        PartiallySelectableText()
        AnnotatedClickableText()
    }
}