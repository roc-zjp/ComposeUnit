package com.zjp.compose_unit.ui.developer

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.zjp.collection.R
import com.zjp.collection.collection.AutoNestScroll
import com.zjp.collection.collection.CollapsingToolbarLayout
import com.zjp.collection.ui.CollectionItem
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.common.state.CommonUiState
import com.zjp.common.utils.recomposeHighlighter
import com.zjp.core_database.model.Compose
import kotlinx.coroutines.newFixedThreadPoolContext
import java.lang.Math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeveloperScreen(
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = {
            UnitTopAppBar(
                title = { Text(text = "开发者页面") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = {
            AutoNestScroll()
        }
    )
}


@Composable
fun NestedScrollSample() {
    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
// our offset to collapse toolbar
    val toolbarOffsetHeightPx =

        remember { mutableStateOf(0f) }
// now, let's create connection to the nested scroll system and listen to the scroll
// happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)
    ) {
        // our list with build in nested scroll support that will notify us about its scroll
        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
            items(100) { index ->
                Text(
                    "I'm item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            item {
                Column {
                    repeat(6) {
                        Box(
                            modifier = Modifier
                                .height(128.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                "Scroll here",
                                modifier = Modifier
                                    .border(12.dp, Color.DarkGray)
                                    .padding(24.dp)
                                    .height(150.dp)
                            )
                        }
                    }
                }
            }

            items(100) { index ->
                Text(
                    "I'm item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

        }
        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        )
    }
}

@Composable
fun NestedScrollDispatcherTest() {
    val maxHeight = 200f;
    val minHeight = 60f

    val d = LocalDensity.current.density
    val toolbarHeightPx = with(LocalDensity.current) {
        maxHeight.dp.roundToPx().toFloat()
    }
    val toolbarMinHeightPx = with(LocalDensity.current) {
        minHeight.dp.roundToPx().toFloat()
    }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value =
                    newOffset.coerceIn(toolbarMinHeightPx - toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = toolbarOffsetHeightPx.value) {
        progress =
            ((toolbarHeightPx + toolbarOffsetHeightPx.value) / toolbarHeightPx - minHeight / maxHeight) / (1f - minHeight / maxHeight)
    }
    Box(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(contentPadding = PaddingValues(top = maxHeight.dp)) {
            items(100) { index ->
                Text(
                    "I'm item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(((toolbarHeightPx + toolbarOffsetHeightPx.value) / d).dp)
                .background(
                    Color.Red
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(24.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(progress + 0.001f)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(vertical = 10.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = com.zjp.compose_unit.R.mipmap.ic_launcher),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        "Hello World",
                        color = Color.White,
                        modifier = Modifier
                            .alpha(progress)
                            .padding((8 * (progress * progress * progress)).dp),
                        fontSize = (24 * (progress)).sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    "Hello World",
                    color = Color.White,
                    modifier = Modifier
                        .alpha(1f - progress)
                        .weight(1.001f - progress)
                        .padding(start = 20.dp),
                    fontSize = (24 * (1f - progress)).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Spacer(modifier = Modifier.width(24.dp))
            }
        }
    }
}


fun Modifier.customDraw(
    color: Color,
    starCount: Int = 5,
    checked: Boolean = false,
) =
    this.then(CustomDrawModifier(color, starCount, checked = checked))

class CustomDrawModifier(
    private val color: Color,
    private val starCount: Int = 5,//星的数量
    private var checked: Boolean = false,
) :
    DrawModifier {
    override fun ContentDrawScope.draw() {

        val radiusOuter = if (size.width > size.height) size.height / 2 else size.width / 2 //五角星外圆径
        val radiusInner = radiusOuter / 2 //五角星内圆半径
        val startAngle = (-Math.PI / 2).toFloat() //开始绘制点的外径角度
        val perAngle = (2 * Math.PI / starCount).toFloat() //两个五角星两个角直接的角度差
        val outAngles = (0 until starCount).map {
            val angle = it * perAngle + startAngle
            Offset(radiusOuter * cos(angle), radiusOuter * sin(angle))
        }//所有外圆角的顶点
        val innerAngles = (0 until starCount).map {
            val angle = it * perAngle + perAngle / 2 + startAngle
            Offset(radiusInner * cos(angle), radiusInner * sin(angle))
        }//所有内圆角的顶点
        val path = Path()//绘制五角星的所有内圆外圆的点连接线
        (0 until starCount).forEachIndexed { index, _ ->
            val outerX = outAngles[index].x
            val outerY = outAngles[index].y
            val innerX = innerAngles[index].x
            val innerY = innerAngles[index].y
//            drawCircle(Color.Red, radius = 3f, center = outAngles[index])
//            drawCircle(Color.Yellow, radius = 3f, center = innerAngles[index])
            if (index == 0) {
                path.moveTo(outerX, outerY)
                path.lineTo(innerX, innerY)
                path.lineTo(
                    outAngles[(index + 1) % starCount].x,
                    outAngles[(index + 1) % starCount].y
                )
            } else {
                path.lineTo(innerX, innerY)//移动到内圆角的端点
                path.lineTo(
                    outAngles[(index + 1) % starCount].x,
                    outAngles[(index + 1) % starCount].y
                )//连接到下一个外圆角的端点
            }
            if (index == starCount - 1) {
                path.close()
            }
        }
        translate(size.width / 2, size.height / 2) {
            drawPath(path, color, style = if (checked) Fill else Stroke(width = 5f))
        }
    }

}











