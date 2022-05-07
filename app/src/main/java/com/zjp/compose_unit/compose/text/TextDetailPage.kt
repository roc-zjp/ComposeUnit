package com.zjp.compose_unit.compose.text

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.compose.common.ComposeHeadView
import com.zjp.compose_unit.compose.common.DividerView
import com.zjp.compose_unit.ui.theme.Compose_unitTheme


@Composable
fun TextDetailView(navController: NavController, viewModel: ShareViewModel) {
    Scaffold(topBar = {
        TopAppBar(
            title = { viewModel.composeItem?.name?.let { Text(text = it) } },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
    }) {
        var scrollState = rememberScrollState()

        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)
        ) {
            ComposeHeadView(item = viewModel.composeItem!!)
            DividerView(title = "基本设置")

            Text(
                "Hello World",
                color = Color.Blue,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(150.dp),
                fontFamily = FontFamily.SansSerif
            )
            DividerView(title = "富文本显示")
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
                }, modifier = Modifier.padding(start = 10.dp)
            )
            DividerView(title = "分段富文本显示")
            Text(
                buildAnnotatedString {
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
                }, modifier = Modifier.padding(start = 10.dp)
            )

            DividerView(title = "行数上限")
            Text("hello ".repeat(50), maxLines = 2, modifier = Modifier.padding(start = 10.dp))
            DividerView(title = "文字溢出")
            Text(
                "Hello Compose ".repeat(50),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp)
            )

            DividerView(title = "选择文字")
            PartiallySelectableText()
            DividerView(title = "获取点击文字的位置")
            ClickableText(
                text = AnnotatedString("Click Me"),
                onClick = { offset ->
                    Log.d("ClickableText", "$offset -th character is clicked.")
                }, modifier = Modifier.padding(start = 10.dp)
            )
            DividerView(title = "可点击文字")
            AnnotatedClickableText(navController)
        }
    }


}

@Composable
fun AnnotatedClickableText(navController: NavController) {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        append("Click ")

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("here")
        }

        pop()
    }

    ClickableText(
        modifier = Modifier.padding(start = 10.dp),
        text = annotatedText,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL", start = offset,
                end = offset
            )
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

@Composable
fun PartiallySelectableText() {
    SelectionContainer {
        Column(modifier = Modifier.padding(start = 10.dp)) {
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


@Preview(showBackground = true)
@Composable
fun ComposeItemPreview() {
    Compose_unitTheme(darkTheme = false) {
        var navController = rememberNavController()
        TextDetailView(navController = navController, ShareViewModel())
    }
}