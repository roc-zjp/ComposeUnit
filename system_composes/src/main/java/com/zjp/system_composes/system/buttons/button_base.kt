package com.zjp.system_composes.system.buttons

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 *  基本样式
 * 【onClick】：事件【Function】
 * 【shape】：外形【Shape】
 * 【border】：边框【BorderStroke】
 * 【colors】：按钮内容、背景和不可用时的颜色【ButtonColors】
 *
 */
@Composable
fun ButtonBase() {
    Row() {
        Spacer(modifier = Modifier.weight(10f))
        Button(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            border = BorderStroke(2.dp, Color.Green)
        ) {
            Text(text = "Button")
        }
        Spacer(modifier = Modifier.weight(10f))
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.Green)
        ) {
            Text(text = "Button")
        }
        Spacer(modifier = Modifier.weight(10f))
        Button(
            onClick = { /*TODO*/ },
            shape = CutCornerShape(10.dp),
            border = BorderStroke(
                2.dp,
                Brush.radialGradient(listOf(Color.White, Color.Red, Color.Green, Color.Black))
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text(text = "Button")
        }
        Spacer(modifier = Modifier.weight(10f))
    }

}

@Composable
fun ButtonWithCustomColor() {
    Button(
        onClick = {
            //your onclick code
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    )

    {
        Text(text = "Button with gray background", color = Color.White)
    }
}

/**
 * 多文本按钮
 */
@Composable
fun ButtonWithMultipleText() {
    Button(
        onClick = {
            //your onclick code
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
    )

    {
        Text(text = "Click ", color = Color.Magenta)
        Text(text = "Here", color = Color.Green)
    }
}

/**
 * 文字带图标
 */
@Composable
fun ButtonWithIcon() {
    Button(onClick = {}) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = "Button With Icon"
        )
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

/**
 * 没有Button的默认背景和边框
 *
 */
@Composable
fun TextButtonBase() {
    TextButton(onClick = { /*TODO*/ }) {
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun OutlinedButtonBase() {
    Column {
        OutlinedButton(onClick = { }) {
            Text(text = "OutlineButton", Modifier.padding(start = 10.dp))
        }
        Button(onClick = { }) {
            Text(text = "Button")
        }
    }
}

/**
 * 取消按钮内边距
 * 【contentPadding】：子组合和边框的间距【PaddingValues】
 * 【enabled】：是否可用【Boolean】
 */
@Composable
fun ButtonClearPadding() {
    Button(
        onClick = {},
        enabled = false,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_add),
            contentDescription = "Button With Icon"
        )
        Text(text = "Add to cart", Modifier.padding(start = 10.dp))
    }
}

/**
 * 【checked】：Checkbox 是选中还是未选中
 * 【onCheckedChange】：单击复选框时要调用的回调
 * 【modifier】：应用于复选框布局的修饰符
 * 【enabled】：组件是启用还是灰显
 * 【interactionSource】：Interaction您可以创建并传入自己记住的内容。
 * 【colors】:不同状态下复选标记/框/边框的颜色。CheckboxDefaults.colors
 */
@Composable
fun CheckBoxBase() {
    var checked by remember { mutableStateOf(false) }
    var customChecked by remember { mutableStateOf(false) }

    Row {
        Row(modifier = Modifier.height(20.dp)) {
            Row {
                Checkbox(
                    checked = customChecked,
                    onCheckedChange = {
                        customChecked = it
                    }
                )
                Text(text = "默认样式")
            }
            Row(modifier = Modifier.height(20.dp)) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    }, colors = CheckboxDefaults.colors(
                        checkedColor = Color.Red,
                        uncheckedColor = Color.Gray
                    )
                )
                Text(text = "自定义样式")
            }
        }
    }

}

@Composable
fun TriStateCheckboxBase() {
    Column {
        // define dependent checkboxes states
        val (state, onStateChange) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(true) }

        // TriStateCheckbox state reflects state of dependent checkboxes
        val parentState = remember(state, state2) {
            if (state && state2) ToggleableState.On
            else if (!state && !state2) ToggleableState.Off
            else ToggleableState.Indeterminate
        }
        // click on TriStateCheckbox can set state for dependent checkboxes
        val onParentClick = {
            val s = parentState != ToggleableState.On
            onStateChange(s)
            onStateChange2(s)
        }

        TriStateCheckbox(
            state = parentState,
            onClick = onParentClick,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(Modifier.size(25.dp))
        Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            Checkbox(state, onStateChange)
            Spacer(Modifier.size(25.dp))
            Checkbox(state2, onStateChange2)
        }
    }
}


@Composable
fun ChipBase() {

    Text(text = "material3 不可用")


}

fun onClick(context: Context) {
    Toast.makeText(context, "点击", Toast.LENGTH_SHORT).show()
}


@Composable
fun ToggleButtonBase() {

    var toggled by remember {
        mutableStateOf(false)
    }
    IconToggleButton(checked = toggled, onCheckedChange = { toggled = it }) {
        val tint by animateColorAsState(if (toggled) Color(0xFFEC407A) else Color(0xFFB0BEC5))
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
    }
}


@Composable
fun AlertDialogBase(openDialog: Boolean, dismiss: () -> Unit) {

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                dismiss()
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismiss()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}


@Composable
fun BackdropScaffoldBase() {

    Text(text = "BackdropScaffold material3 不可用")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeBase() {
    BadgedBox(badge = { Badge { Text("8") } }) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite"
        )
    }
}


//@Composable
//fun BottomAppBarBase() {
//    BottomAppBar(cutoutShape = CircleShape) {
//        // Leading icons should typically have a high content alpha
//        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
//            }
//        }
//        // The actions should be at the end of the BottomAppBar. They use the default medium
//        // content alpha provided by BottomAppBar
//        Spacer(Modifier.weight(1f, true))
//        IconButton(onClick = { /* doSomething() */ }) {
//            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
//        }
//        IconButton(onClick = { /* doSomething() */ }) {
//            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
//        }
//    }
//}


@Composable
fun DropdownMenuBase() {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = { Text("Refresh") }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = { Text("Settings") }, onClick = { /*TODO*/ })
            DropdownMenuItem(text = { Text("Send Feedback") }, onClick = { /*TODO*/ })

        }
    }

}


@Composable
fun DropdownMenuItemBase() {
    DropdownMenuItem(text = { Text("Send Feedback") }, onClick = { /*TODO*/ })


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxBase() {
    val options = listOf("Android", "ios", "windows", "macos", "linux")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedOptionText,
            onValueChange = { selectedOptionText = it },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        // filter options based on text field value
        val filteringOptions =
            options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        if (filteringOptions.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                filteringOptions.forEach { selectionOption ->

                    DropdownMenuItem(text = { Text(text = selectionOption) }, onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    })


                }
            }
        }
    }
}

/**
 * 用法同FloatingActionButton,提供了icon和Text两个插槽
 */
@Composable
fun ExtendedFloatingActionButtonBase() {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
        text = { Text("FLUID FAB") },
        onClick = { /*do something*/ },
        modifier = Modifier.fillMaxWidth()
    )
}

/**
 * 用法同FloatingActionButton,提供了icon和Text两个插槽
 */
@Composable
fun FloatingActionButtonBase() {
    FloatingActionButton(
        onClick = { /*do something*/ },
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        containerColor = Color.Magenta,
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = null)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipBase() {
    val state = remember { mutableStateOf(false) }
    FilterChip(
        selected = state.value,
        onClick = { state.value = !state.value },
        label = { Text("Filter chip") })

}

@Composable
fun IconToggleButtonBase() {
    var checked by remember { mutableStateOf(false) }

    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
        val tint by animateColorAsState(if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5),
            label = ""
        )
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
    }
}

@Composable
fun IconButtonBase() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
    }
}


@Preview
@Composable
fun ButtonBasePreview() {
    Column {
        IconButtonBase()
    }
}

