package com.zjp.system_composes.system.buttons

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjp.system_composes.system.widgets.ProgressCollection
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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
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
    OutlinedButton(onClick = { /*TODO*/ }) {
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipBase() {
//    Chip(
//        onClick = { /* Do something! */ },
//        border = ChipDefaults.outlinedBorder,
//        colors = ChipDefaults.outlinedChipColors(),
//        leadingIcon = {
//            Icon(
//                Icons.Filled.Settings,
//                contentDescription = "Localized description"
//            )
//        }
//    ) {
//        Text("Change settings")
//    }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffoldBase() {
    val scope = rememberCoroutineScope()
    val selection = remember { mutableStateOf(1) }
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    LaunchedEffect(scaffoldState) {
        scaffoldState.reveal()
    }
    BackdropScaffold(
        scaffoldState = scaffoldState,

        appBar = {
            TopAppBar(
                title = { Text("Backdrop scaffold") },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Localized description")
                        }
                    } else {
                        IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                            Icon(Icons.Default.Close, contentDescription = "Localized description")
                        }
                    }
                },
                actions = {
                    var clickCount by remember { mutableStateOf(0) }
                    IconButton(
                        onClick = {
                            // show snackbar as a suspend function
                            scope.launch {
                                scaffoldState.snackbarHostState
                                    .showSnackbar("Snackbar #${++clickCount}")
                            }
                        }
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Localized description")
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(if (selection.value >= 3) 3 else 5) {
                    ListItem(
                        Modifier.clickable {
                            selection.value = it
                            scope.launch { scaffoldState.conceal() }
                        },
                        text = { Text("Select $it") }
                    )
                }
            }
        },
        frontLayerContent = {
            Text("Selection: ${selection.value}")
            LazyColumn(modifier = Modifier.height(200.dp)) {
                items(50) {
                    ListItem(
                        text = { Text("Item $it") },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun BadgeBase() {
    BadgedBox(badge = { Badge { Text("8") } }) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite"
        )
    }
}


@Composable
fun BottomAppBarBase() {
    BottomAppBar(cutoutShape = CircleShape) {
        // Leading icons should typically have a high content alpha
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
            }
        }
        // The actions should be at the end of the BottomAppBar. They use the default medium
        // content alpha provided by BottomAppBar
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
        }
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonDrawerBase() {
    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = gesturesEnabled,
                    onValueChange = toggleGesturesEnabled
                )
        ) {
            Checkbox(gesturesEnabled, null)
            Text(text = if (gesturesEnabled) "Gestures Enabled" else "Gestures Disabled")
        }
        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
        BottomDrawer(
            gesturesEnabled = gesturesEnabled,
            drawerState = drawerState,
            drawerContent = {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    onClick = { scope.launch { drawerState.close() } },
                    content = { Text("Close Drawer") }
                )
                LazyColumn {
                    items(25) {
                        ListItem(
                            text = { Text("Item $it") },
                            icon = {
                                Icon(
                                    Icons.Default.Favorite,
                                    contentDescription = "Localized description"
                                )
                            }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val openText = if (gesturesEnabled) "▲▲▲ Pull up ▲▲▲" else "Click the button!"
                    Text(text = if (drawerState.isClosed) openText else "▼▼▼ Drag down ▼▼▼")
                    Spacer(Modifier.height(20.dp))
                    Button(onClick = { scope.launch { drawerState.open() } }) {
                        Text("Click to open")
                    }
                }
            }
        )
    }
}



@Composable
fun DropdownMenuBase() {
    var expanded by remember { mutableStateOf(false) }
    ProgressCollection()

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
            DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                Text("Refresh")
            }
            DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                Text("Settings")
            }
            Divider()
            DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                Text("Send Feedback")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxBase() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
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
            label = { Text("Label") },
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
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
    }
}

@Composable
fun ExtendedFloatingActionButtonBase() {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
        text = { Text("FLUID FAB") },
        onClick = { /*do something*/ },
        modifier = Modifier.fillMaxWidth()
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChipBase() {
    val state = remember { mutableStateOf(false) }
//    FilterChip(
//        selected = state.value,
//        onClick = { state.value = !state.value },
//        border = ChipDefaults.outlinedBorder,
//        colors = ChipDefaults.outlinedFilterChipColors(),
//        selectedIcon = {
//            Icon(
//                imageVector = Icons.Filled.Done,
//                contentDescription = "Localized Description",
//                modifier = Modifier.requiredSize(ChipDefaults.SelectedIconSize)
//            )
//        }) {
//        Text("Filter chip")
//    }
}

@Composable
fun IconToggleButtonBase() {
    var checked by remember { mutableStateOf(false) }

    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
        val tint by animateColorAsState(if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5))
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
    }
}


@Preview
@Composable
fun ButtonBasePreview() {
    Column {
        ButtonBase()
        TextButtonBase()
    }
}


//@Preview
//@Composable
//fun ButtonPre() {
//
////    Column {
////        ButtonBase()
////    }
//
//
////    BottomSheetScaffold(
////        sheetContent = {
////            Box(
////                Modifier
////                    .fillMaxWidth()
////                    .height(128.dp),
////                contentAlignment = Alignment.Center
////            ) {
////                Text("Swipe up to expand sheet")
////            }
////            Column(
////                Modifier
////                    .fillMaxWidth()
////                    .padding(64.dp),
////                horizontalAlignment = Alignment.CenterHorizontally
////            ) {
////                Text("Sheet content")
////                Spacer(Modifier.height(20.dp))
////                Button(
////                    onClick = {
////                        scope.launch { scaffoldState.bottomSheetState.collapse() }
////                    }
////                ) {
////                    Text("Click to collapse sheet")
////                }
////            }
////        },
////        scaffoldState = scaffoldState,
////        topBar = {
////            TopAppBar(
////                title = { Text("Bottom sheet scaffold") },
////                navigationIcon = {
////                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
////                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
////                    }
////                }
////            )
////        },
////        floatingActionButton = {
////            var clickCount by remember { mutableStateOf(0) }
////            FloatingActionButton(
////                onClick = {
////                    // show snackbar as a suspend function
////                    scope.launch {
////                        scaffoldState.snackbarHostState.showSnackbar("Snackbar #${++clickCount}")
////                    }
////                }
////            ) {
////                Icon(Icons.Default.Favorite, contentDescription = "Localized description")
////            }
////        },
////        floatingActionButtonPosition = FabPosition.End,
////        sheetPeekHeight = 128.dp,
////        drawerContent = {
////            Column(
////                Modifier
////                    .fillMaxWidth()
////                    .padding(16.dp),
////                horizontalAlignment = Alignment.CenterHorizontally
////            ) {
////                Text("Drawer content")
////                Spacer(Modifier.height(20.dp))
////                Button(onClick = { scope.launch { scaffoldState.drawerState.close() } }) {
////                    Text("Click to close drawer")
////                }
////            }
////        }
////    ) { innerPadding ->
////        Column(
////            Modifier
////                .verticalScroll(rememberScrollState())
////                .padding(innerPadding)
////        ) {
////            ButtonShapeAndBorder()
////            ButtonWithCustomColor()
////            ButtonWithMultipleText()
////            ButtonWithIcon()
////            TextButtonBase()
////            OutlinedButtonBase()
////            RadioButtonGroup()
////            CheckBoxBase()
////            SwitchBase()
////            ChipBase()
////            ToggleButtonBase()
////            OutlinedButton(onClick = { openDialog = true }) {
////                Text(text = "showDailog")
////            }
////            AlertDialogBase(openDialog = openDialog) {
////                openDialog = false
////            }
////
////            BackdropScaffoldBase()
////            BadgeBase()
////            BottomAppBarBase()
////            CardBase()
////            DropdownMenuBase()
////            ExposedDropdownMenuBoxBase()
////            ExtendedFloatingActionButtonBase()
////            FilterChipBase()
////            IconToggleButtonBase()
////            ListItem(
////                text = { Text("One line list item with 56x56 icon") },
////                icon = {
////                    Icon(
////                        Icons.Filled.Favorite,
////                        contentDescription = null,
////                        modifier = Modifier.size(56.dp)
////                    )
////                }
////            )
////
////        }
////    }
//
//
//}