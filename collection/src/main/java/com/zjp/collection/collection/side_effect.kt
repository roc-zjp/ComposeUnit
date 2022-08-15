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
fun LandingScreen(onTimeout: () -> Unit) {

    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(true) {
        delay(3000)
        currentOnTimeout()
    }

    /* Landing screen content */
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
    var show by remember {
        mutableStateOf(true)
    }
    LogUtils.d("RememberUpdatedStateBase重构")

    Column {
        Text(text = "点击Increase，count值增加。5S后弹初始值为0的Toast,6S后弹当前最新值的Toast")
        if (show) {
            Button(onClick = { count++ }) {
                Text(text = "Increase $count")
            }
            NormalToast(text = count)
            UpdateToast(text = count)
        } else {
            Button(onClick = { show = !show }) {
                Text(text = "开始")
            }
        }
    }
}

@Composable
fun NormalToast(text: Int) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(5000)
        Toast.makeText(context, "Normal当前值：$text", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun UpdateToast(text: Int) {

    val updateCount = rememberUpdatedState(newValue = text)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        delay(6000)
        Toast.makeText(context, "rememberUpdatedState当前值：${updateCount.value}", Toast.LENGTH_LONG)
            .show()
    }

}


@Composable
fun DisposableEffectBase() {

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
            } else if (event == Lifecycle.Event.ON_STOP) {
            }
        }
        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Red)
    ) {
        Text(text = "感知当前组合的生命周期，当组合移除时，取消生命周期的监听")
    }

}

@Composable
fun SideEffectBase() {
    Text(text = "SideEffect 将 Compose 状态发布为非 Compose 代码,暂时没想到具体的使用场景，带收录")
}

@Composable
fun ProduceStateBase() {
    var show by remember {
        mutableStateOf(false)
    }
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
            CircularProgressIndicator()
        } else {
            Text(text = "load complete")
        }
    } else {
        Button(onClick = { show = true }) {
            Text(text = "开始加载")
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

    LazyColumn(state = listState) {
        items(50) { index ->
            Text(text = "item $index")
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .distinctUntilChanged()//直到发生改变才再次发送
            .filter { it }
            .collect {
                //value:第一个Item是否可见
                Log.d("TAG", "当第一个Item不可见时触发")
            }
    }
}




