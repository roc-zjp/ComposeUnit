package com.zjp.system_composes.system.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBase() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Text(text = "scaffold")
//        val snackbarHostState = remember { SnackbarHostState() }
//        val scope = rememberCoroutineScope()
//        Scaffold(
//            snackbarHost = { SnackbarHost(snackbarHostState) },
//
//            topBar = {
//                TopAppBar(
//                    title = { Text(text = "Compose Unit") },
//                    actions = {
//                        Icon(Icons.Default.Delete, contentDescription = "list")
//                        Icon(Icons.Default.Add, contentDescription = "add")
//                    },
//                    navigationIcon = {
//                        Icon(
//                            Icons.Default.Menu,
//                            contentDescription = "Menu",
//                            modifier = Modifier.clickable {
//                                scope.launch {
//                                    if (scaffoldState.drawerState.isOpen) {
//                                        scaffoldState.drawerState.close()
//                                    } else {
//                                        scaffoldState.drawerState.open()
//                                    }
//                                }
//                            })
//                    },
//                )
//            },
//            bottomBar = {
//                BottomAppBar(cutoutShape = CircleShape) {
//                    BottomNavigation() {
//                        BottomNavigationItem(
//                            selected = true,
//                            onClick = { /*TODO*/ },
//                            label = { Text(text = "Home") },
//                            icon = { Icon(Icons.Default.Home, contentDescription = "home") }
//                        )
//                        BottomNavigationItem(
//                            selected = false,
//                            onClick = { /*TODO*/ },
//                            label = { Text(text = "Person") },
//                            icon = { Icon(Icons.Default.Person, contentDescription = "Preson") }
//                        )
//                    }
//                }
//            },
//            drawerContent = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Red)
//                )
//            },
//            drawerShape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
//            floatingActionButton = {
//                FloatingActionButton(onClick = { /*TODO*/ }) {
//                    Icon(Icons.Default.Add, contentDescription = "add")
//                }
//            },
//            isFloatingActionButtonDocked = true,
//            floatingActionButtonPosition = FabPosition.Center,
//            drawerGesturesEnabled = true
//        ) {
//            Box(modifier = Modifier.padding(it))
//
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBase() {
    TopAppBar(
        title = { Text(text = "Compose Unit") },
        actions = {
            Icon(Icons.Default.Delete, contentDescription = "list")
            Icon(Icons.Default.Add, contentDescription = "add")
        },
        navigationIcon = {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable {

                })
        }
    )
}

@Composable
fun BottomAppBarBase() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Scaffold(
            bottomBar = {
                BottomAppBar() {
                    // Leading icons should typically have a high content alpha
//                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
//                        IconButton(onClick = { /* doSomething() */ }) {
//                            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
//                        }
//                    }
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
            },

            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                    }
                }
            }

        ) {
            Box(modifier = Modifier.padding(it))
        }
    }
}


@Composable
fun BottomNavigationBase() {
    Text(text = "BottomNavigation")
//    BottomNavigation {
//        BottomNavigationItem(
//            selected = false,
//            onClick = { /*TODO*/ },
//            icon = {
//                Icon(
//                    Icons.Default.Home,
//                    contentDescription = "home"
//                )
//            },
//            label = {
//                Text(text = "Person")
//            })
//        BottomNavigationItem(
//            selected = true,
//            onClick = { /*TODO*/ },
//            icon = {
//                Icon(
//                    Icons.Default.Person,
//                    contentDescription = "person"
//                )
//            },
//            label = {
//                Text(text = "Person")
//            }
//        )
//    }
}

/**
 * 【header】：头部显示内容，一般的是一个FloatingActionButton或者一个logo
 */
@Composable
fun NavigationRailItemBase() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "Settings")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Settings)
    NavigationRail(header = {
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorite")
        }
    }) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}


@Composable
fun ModalBottomSheetLayoutBase() {
    Text(text = "ModalBottomSheetLayout")
//    var skipHalfExpanded by remember { mutableStateOf(false) }
//
//    val state = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden,
//    )
//    val scope = rememberCoroutineScope()
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(500.dp)
//    ) {
//        ModalBottomSheetLayout(
//            sheetState = state,
//            sheetContent = {
//                LazyColumn {
//                    items(50) {
//                        ListItem(
//                            text = { Text("Item $it") },
//                            icon = {
//                                Icon(
//                                    Icons.Default.Favorite,
//                                    contentDescription = "Localized description"
//                                )
//                            }
//                        )
//                    }
//                }
//            }
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Row(
//                    Modifier.toggleable(
//                        value = skipHalfExpanded,
//                        role = Role.Checkbox,
//                        onValueChange = { checked -> skipHalfExpanded = checked }
//                    )
//                ) {
//                    Checkbox(checked = skipHalfExpanded, onCheckedChange = null)
//                    Spacer(Modifier.width(16.dp))
//                    Text("Skip Half Expanded State")
//                }
//                Spacer(Modifier.height(20.dp))
//                Button(onClick = { scope.launch { state.show() } }) {
//                    Text("Click to show sheet")
//                }
//            }
//        }
//    }
}

@Composable
fun ModalDrawerBase() {
    Text(text = "ModalDrawer")

//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(500.dp)
//    ) {
//        val drawerState = rememberDrawerState(DrawerValue.Closed)
//        val scope = rememberCoroutineScope()
//        ModalDrawer(
//            drawerState = drawerState,
//            drawerContent = {
//                Button(
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(top = 16.dp),
//                    onClick = { scope.launch { drawerState.close() } },
//                    content = { Text("Close Drawer") }
//                )
//            },
//            content = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
//                    Spacer(Modifier.height(20.dp))
//                    Button(onClick = { scope.launch { drawerState.open() } }) {
//                        Text("Click to open")
//                    }
//                }
//            }
//        )
//    }
}



@Composable
fun BottomDrawerBase() {
    Text(text = "BottomDrawer")
//    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
//    val scope = rememberCoroutineScope()
//    Column {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .toggleable(
//                    value = gesturesEnabled,
//                    onValueChange = toggleGesturesEnabled
//                )
//        ) {
//            Checkbox(gesturesEnabled, null)
//            Text(text = if (gesturesEnabled) "Gestures Enabled" else "Gestures Disabled")
//        }
//        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(500.dp)
//        ) {
//            BottomDrawer(
//                gesturesEnabled = gesturesEnabled,
//                drawerState = drawerState,
//                drawerContent = {
//                    Button(
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally)
//                            .padding(top = 16.dp),
//                        onClick = { scope.launch { drawerState.close() } },
//                        content = { Text("Close Drawer") }
//                    )
//                    LazyColumn {
//                        items(25) {
//                            ListItem(
//                                text = { Text("Item $it") },
//                                icon = {
//                                    Icon(
//                                        Icons.Default.Favorite,
//                                        contentDescription = "Localized description"
//                                    )
//                                }
//                            )
//                        }
//                    }
//                },
//                content = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(16.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        val openText =
//                            if (gesturesEnabled) "▲▲▲ Pull up ▲▲▲" else "Click the button!"
//                        Text(text = if (drawerState.isClosed) openText else "▼▼▼ Drag down ▼▼▼")
//                        Spacer(Modifier.height(20.dp))
//                        Button(onClick = { scope.launch { drawerState.open() } }) {
//                            Text("Click to open")
//                        }
//                    }
//                }
//            )
//        }
//    }
}

@Composable
fun SnackbarBase() {
    Text(text = "SnackBar")

//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//    Scaffold(
//        modifier = Modifier.height(400.dp),
//        scaffoldState = scaffoldState,
//        floatingActionButton = {
//            var clickCount by remember { mutableStateOf(0) }
//            ExtendedFloatingActionButton(
//                text = { Text("Show Snackbar") },
//                onClick = {
//                    // show snackbar as a suspend function
//                    scope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
//                    }
//                }
//            )
//        },
//        content = { innerPadding ->
//            Text(
//                text = "Body content",
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//                    .wrapContentSize()
//            )
//        }
//    )
}

@Composable
fun CustomSnackbar() {
    Text(text = "CustomSnackbar")
//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//    Scaffold(
//        modifier = Modifier.height(400.dp),
//        scaffoldState = scaffoldState,
//        snackbarHost = {
//            // reuse default SnackbarHost to have default animation and timing handling
//            SnackbarHost(it) { data ->
//                // custom snackbar with the custom border
//                Snackbar(
//                    modifier = Modifier.border(2.dp, MaterialTheme.colors.secondary),
//                    snackbarData = data
//                )
//            }
//        },
//        floatingActionButton = {
//            var clickCount by remember { mutableStateOf(0) }
//            ExtendedFloatingActionButton(
//                text = { Text("Show Snackbar") },
//                onClick = {
//                    scope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
//                    }
//                }
//            )
//        },
//        content = { innerPadding ->
//            Text(
//                text = "Custom Snackbar Demo",
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//                    .wrapContentSize()
//            )
//        }
//    )
}

@Preview
@Composable
fun ScaffoldPreview() {
    ScaffoldBase()
}