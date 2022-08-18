package com.zjp.collection.collection

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.apkfuns.logutils.LogUtils
import com.zjp.common.state.CommonUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * 减少不必要的重构
 */
@Composable
fun DerivedState() {
    LogUtils.d("re composed")
    var counter by remember {
        mutableStateOf(0)
    }
    val counterText by derivedStateOf {
        "The counter is $counter"
    }
    Button(onClick = { counter++ }) {
        Text(text = counterText)
    }
}


@Composable
fun SnapshotFlow(scaffoldState: ScaffoldState) {

    var message by remember {
        mutableStateOf(System.currentTimeMillis())
    }
    LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
        snapshotFlow { scaffoldState.snackbarHostState }
            .mapNotNull { it.currentSnackbarData?.message }
            .distinctUntilChanged()
            .collect() { message ->
                LogUtils.d("A Snackbar with message $message was shown")
            }
    }

    var scope = rememberCoroutineScope()

    Column() {
        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("")
            }
        }) {
            Text(text = "empty message")
        }
        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message.toString())
            }
        }) {
            Text(text = "same message")
        }

        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(System.currentTimeMillis().toString())
            }
        }) {
            Text(text = "different message")
        }
    }
}


@Composable
fun TwoButtonScreen() {
    var buttonColour by remember {
        mutableStateOf("Unknown")
    }
    Column {
        Button(
            onClick = {
                buttonColour = "Red"
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            )
        ) {
            Text("Red Button")
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                buttonColour = "Black"
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black
            )
        ) {
            Text("Black Button")
        }
        Timer(buttonColor = buttonColour)
    }
}

@Composable
fun Timer(
    buttonColor: String
) {
    LogUtils.d("re compose")
    val timerDuration = 5000L
    println("Composing timer with colour : $buttonColor")
    LaunchedEffect(key1 = buttonColor, block = {
        delay(timerDuration)
        println("Timer ended")
        println("Last pressed button color was $buttonColor")
    })

}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LaunchedEffectBase() {

    val scaffoldState = rememberScaffoldState()
    var hasError by remember {
        mutableStateOf(false)
    }

    // If the UI state contains an error, show snackbar
    if (hasError) {

        // `LaunchedEffect` will cancel and re-launch if
        // `scaffoldState.snackbarHostState` changes
        LaunchedEffect(scaffoldState.snackbarHostState) {
            // Show snackbar using a coroutine, when the coroutine is cancelled the
            // snackbar will automatically dismiss. This coroutine will cancel whenever
            // `state.hasError` is false, and only start when `state.hasError` is true
            // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message",
                actionLabel = "Retry message"
            )
        }
    }
    Box(modifier = Modifier.height(200.dp)) {
        Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
            FloatingActionButton(onClick = { hasError = !hasError }) {
                Text(text = "Press me")
            }
        }) {
            Box(Modifier.fillMaxSize()) {
                Text(text = "点击Press me,弹出错误提示", modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CoroutineScopeBase() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Box(modifier = Modifier.height(200.dp)) {
        Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Something happened!")
                }
            }) {
                Text(text = "Press me")
            }
        }) {

        }
    }
}


@Composable
fun RememberUpdatedStateBase() {
    var count by remember {
        mutableStateOf(0)
    }

    var start by remember {
        mutableStateOf(false)
    }



    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "点击开始，1S后更改count的值。\n2S后弹出没有rememberUpdatedState的值的Toast\n3S后弹出rememberUpdatedState的值得Toast,")
        if (!start) {
            Button(onClick = { start = true }) {
                Text(text = "开始")
            }
        } else {
            LaunchedEffect(true) {
                delay(1000)
                count = Random.nextInt()
            }
            Column {
                NormalToast(text = count)
                UpdateToast(text = count)
                Button(onClick = { start = false }) {
                    Text(text = "再来一次")
                }
            }
        }
    }
}

@Composable
fun NormalToast(text: Int) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(2000)
        Toast.makeText(context, "没有rememberUpdatedState的值：$text", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun UpdateToast(text: Int) {
    val updateCount = rememberUpdatedState(newValue = text)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(3000)
        Toast.makeText(context, "rememberUpdatedState的值：${updateCount.value}", Toast.LENGTH_LONG)
            .show()
    }
}


@Composable
fun DisposableEffectBase() {

    var removed by remember {
        mutableStateOf(true)
    }
    var lifeStr by remember {
        mutableStateOf("")
    }



    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = lifeStr)

        if (removed) {
            Button(onClick = {
                removed = false
            }) {
                Text(text = "添加")
            }
        } else {
            Column {
                LifecycleBase() {
                    lifeStr += it
                }
                Button(onClick = { removed = true }) {
                    Text(text = "移除")
                }
            }
        }
    }
}

@Composable
fun LifecycleBase(onLifecycle: (name: String) -> Unit) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            onLifecycle("\n${event.name}")
        }
        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun SideEffectBase() {

    var count by remember {
        mutableStateOf(1)
    }
    Column {
        SideEffectReComposition(count = count)

        Button(onClick = { count++ }) {
            Text(text = "重组")
        }
    }
}

@Composable
fun SideEffectReComposition(count: Int) {
    val context = LocalContext.current
    SideEffect {
        Toast.makeText(context, "重组了", Toast.LENGTH_SHORT).show()
    }
    Text(text = "重组：$count 次")
}

@Composable
fun ProduceStateBase() {
    var show by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        if (show) {
            val compose = produceState<CommonUiState>(
                initialValue = CommonUiState.NoData(isLoading = true, emptyList()),
                key1 = true
            ) {
                delay(5000)
                value = CommonUiState.NoData(isLoading = false, emptyList())

                awaitDispose {

                }

            }
            if (compose.value.isLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            } else {
                Text(text = "load complete", Modifier.align(Alignment.Center))
            }
        } else {

            Button(onClick = { show = true }, Modifier.align(Alignment.Center)) {
                Text(text = "开始加载")
            }

        }
    }

}

@Composable
fun DerivedStateOfBase(
    highPriorityKeywords: List<String> = listOf(
        "Review",
        "Unblock",
        "Compose"
    )
) {

    val todoTasks =
        remember { mutableStateListOf<String>("android", "ios", "java", "c", "Compose") }

    // Calculate high priority tasks only when the todoTasks or highPriorityKeywords
    // change, not on every recomposition
    val highPriorityTasks by remember(highPriorityKeywords) {
        derivedStateOf {
            todoTasks.filter {
                highPriorityKeywords.contains(it)
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn {
            items(highPriorityTasks) {
                Text(text = it, color = Color.Red)
            }
            items(todoTasks) {
                Text(text = it, color = Color.Gray)
            }
            item {
                Button(onClick = { todoTasks.add("Review") }) {
                    Text(text = "添加Review")
                }
            }
        }
        /* Rest of the UI where users can add elements to the list */
    }
}


@Composable
fun SnapshotFlowBase() {
    val listState = rememberLazyListState()

    var backToTop by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    Box {
        LazyColumn(state = listState) {
            items(50) { index ->
                Text(text = "item $index", modifier = Modifier.fillMaxWidth())
            }
        }
        if (backToTop) {
            Button(onClick = {
                scope.launch { listState.scrollToItem(index = 0) }
            }, modifier = Modifier.align(Alignment.BottomEnd)) {
                Text(text = "返回顶部")
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .distinctUntilChanged()//直到发生改变才再次发送
            .collect {
                //value:第一个Item是否可见
                backToTop = it
            }
    }
}




