package com.zjp.system_composes.system.text

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//输入和修改文字
/**
 * 【value】：当前值【String】
 * 【onValueChange】：输入事件【Function】
 * 【label】：标签【Composable】
 * 【placeholder】：占位符【Composable】
 * 【leadingIcon】：前面的Icon【Composable】
 * 【trailingIcon】：后面的Icon【Composable】
 * 【isError】：是否错误，如果错误背景会变红【Boolean】
 * 【maxLines】：最大输入行数【Int】
 * 【readOnly】：是否只读【Boolean】
 * 【singleLine】：是否单行【Boolean】
 * 【keyboardActions】：onDone、onNext、onPrevious、onSearch、onSend、onGo 键盘按钮点击回调，对应着 keyboardOptions.imeAction的类型【KeyboardActions】
 * 【keyboardOptions】：首字母是否大小写、是否自动纠错、输入类型、键盘按钮类型【KeyboardOptions】
 */
@Composable
fun SimpleFilledTextFieldSample() {

    var text by remember { mutableStateOf("Hello") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") },
        placeholder = { Text("placeholder") },
        leadingIcon = { Text("leadingIcon") },
        trailingIcon = { Text("trailingIcon") },
        isError = true,
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        maxLines = 10,
        singleLine = false,
        keyboardActions = KeyboardActions(
            onDone = {

            },
            onNext = {

            },
            onPrevious = {

            },
            onSearch = {

            },
            onSend = {

            },
            onGo = {

            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters,
            autoCorrect = true,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Search
        ),
        readOnly = false
    )

}

/**
 * 【onValueChange】：输入回调
 * 【label】：未获得焦点显示在文本框内，获得焦点显示在文本边框上
 */
@Composable
fun SimpleOutlinedTextFieldSample() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

/**
 * 【TextRange】：选择范围
 *
 */
@Composable
fun OutlinedTextFieldCursor() {
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("example", TextRange(0, 7)))
    }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )
}

//风格设置
@Composable
fun StyledTextField() {
    var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text("Enter text") },
        maxLines = 2,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(20.dp)
    )
}

//格式设置
/**
 * 【visualTransformation】：改变输入框视觉输出【VisualTransformation】
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordTextField() {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var password by rememberSaveable { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .onFocusEvent {
                coroutineScope.launch {
                    delay(300)
                    bringIntoViewRequester.bringIntoView()
                }
            },
    )
}

//清理输入
@Composable
fun NoLeadingZeroes() {
    var input by rememberSaveable { mutableStateOf("") }
    TextField(
        value = input,
        onValueChange = { newText ->
            input = newText.trimStart { it == '0' }
        }
    )
}


@Composable
fun BasicTextFieldBase() {
    var key by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = key,
        onValueChange = {
            key = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 30.dp),
        textStyle = TextStyle(
            textAlign = TextAlign.Justify,
            fontSize = 20.sp,
            color = Color.Black.copy(alpha = 0.8f)
        ),
        singleLine = true,
        cursorBrush = SolidColor(Color.Red),
        keyboardActions = KeyboardActions(
            onSearch = {

            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        decorationBox = { innerTextField ->
            Box() {
                if (key.isEmpty()) {
                    Text("搜点啥", color = Color.Gray, fontSize = 20.sp)
                }
                innerTextField()  //<-- Add this
            }
        }
    )
}


@Composable
fun CreditCardBasicTextField() {
    /** The offset translator used for credit card input field */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset < 4 -> offset
                offset < 8 -> offset + 1
                offset < 12 -> offset + 2
                offset <= 16 -> offset + 3
                else -> 19
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset <= 4 -> offset
                offset <= 9 -> offset - 1
                offset <= 14 -> offset - 2
                offset <= 19 -> offset - 3
                else -> 16
            }
        }
    }

    /**
     * Converts up to 16 digits to hyphen connected 4 digits string. For example,
     * "1234567890123456" will be shown as "1234-5678-9012-3456"
     */
    val creditCardTransformation = VisualTransformation { text ->
        val trimmedText = if (text.text.length > 16) text.text.substring(0..15) else text.text
        var transformedText = ""
        trimmedText.forEachIndexed { index, char ->
            transformedText += char
            if ((index + 1) % 4 == 0 && index != 15) transformedText += "-"
        }
        TransformedText(AnnotatedString(transformedText), creditCardOffsetTranslator)
    }

    var text by rememberSaveable { mutableStateOf("") }
    BasicTextField(
        value = text,
        onValueChange = { input ->
            if (input.length <= 16 && input.none { !it.isDigit() }) {
                text = input
            }
        },
        modifier = Modifier
            .size(170.dp, 30.dp)
            .background(Color.LightGray)
            .wrapContentSize(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = creditCardTransformation
    )
}


@Composable
fun BasicTextBase() {
    val myId = "inlineContent"
    val text = buildAnnotatedString {
        append("Hello")
        // Append a placeholder string "[myBox]" and attach an annotation "inlineContent" on it.
        appendInlineContent(myId, "[myBox]")
        append("br\nbr")
    }

    val inlineContent = mapOf(
        Pair(
            // This tells the [BasicText] to replace the placeholder string "[myBox]" by
            // the composable given in the [InlineTextContent] object.
            myId,
            InlineTextContent(
                // Placeholder tells text layout the expected size and vertical alignment of
                // children composable.
                Placeholder(
                    width = 5.em,
                    height = 5.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                // This [Box] will fill maximum size, which is specified by the [Placeholder]
                // above. Notice the width and height in [Placeholder] are specified in TextUnit,
                // and are converted into pixel by text layout.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red)
                )
            }
        )
    )

    BasicText(
        text = text,
        inlineContent = inlineContent,
        onTextLayout = {
//            LogUtils.d("${it.size},${it.firstBaseline},${it.lastBaseline},${it.multiParagraph}")
        }, softWrap = false
    )
}

@Composable
fun ClickTextBase() {
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
        modifier = Modifier
            .padding(start = 10.dp)
            .background(Color(0xff44D1FD)),
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


@Preview
@Composable
fun TextFieldPreview() {
    Column {
        SimpleFilledTextFieldSample()
        SimpleOutlinedTextFieldSample()
        StyledTextField()
        PasswordTextField()
        NoLeadingZeroes()
    }
}