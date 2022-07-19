package com.zjp.system_composes.system.text

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
@OptIn(ExperimentalFoundationApi::class)
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