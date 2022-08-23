package com.zjp.system_composes.system.containers

import android.content.ClipData
import android.widget.Space
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants

/**
 * 【verticalAlignment】：竖直方向对齐模式【Alignment】
 * 【horizontalArrangement】：水平排列方式【Arrangement.Horizontal】
 */
@Composable
fun RowBase() {
    Row(
        modifier = Modifier
            .height(50.dp)
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "水平布局第一个子组合")
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ArrowForward, contentDescription = "第二个子组合")
    }
}

@Composable
fun RowArrangement() {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        var unfold by remember {
            mutableStateOf(true)
        }
        val maxWidth = (LocalConfiguration.current.screenWidthDp - 40).dp
        val minWidth = 90.dp
        val width: Dp by animateDpAsState(if (unfold) maxWidth else minWidth)


        Button(onClick = { unfold = !unfold }) {
            Text(text = "显示动画")
        }




        Column(modifier = Modifier.width(width)) {
            RowItem(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(10.dp))
            RowItem(horizontalArrangement = Arrangement.SpaceBetween)
            Spacer(modifier = Modifier.height(10.dp))

            RowItem(horizontalArrangement = Arrangement.SpaceAround)
            Spacer(modifier = Modifier.height(10.dp))

            RowItem(horizontalArrangement = Arrangement.SpaceEvenly)
            Spacer(modifier = Modifier.height(10.dp))

            RowItem(horizontalArrangement = Arrangement.Start)
            Spacer(modifier = Modifier.height(10.dp))

            RowItem(horizontalArrangement = Arrangement.Center)
            Spacer(modifier = Modifier.height(10.dp))

            RowItem(horizontalArrangement = Arrangement.End)
        }
    }
}

@Composable
fun RowItem(
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.Gray
            )
    ) {
        Item(title = "A", modifier = modifier)
        Item(title = "B", modifier = modifier)
        Item(title = "C", modifier = modifier)
    }
}

@Composable
fun Item(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .height(20.dp)
            .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)

    ) {
        Text(
            text = title, modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


/**
 * 【horizontalAlignment】：竖直方向对齐模式【Alignment】
 * 【verticalArrangement】：水平排列方式【Arrangement.Vertical】
 */
@Composable
fun ColumnBase() {
    Column(
        modifier = Modifier
            .height(50.dp)
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "水平布局第一个子组合")
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ArrowForward, contentDescription = "第二个子组合")
    }
}


@Composable
fun ColumnArrangement() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var unfold by remember {
            mutableStateOf(true)
        }
        val maxWidth = 300.dp
        val minWidth = 90.dp
        val height: Dp by animateDpAsState(if (unfold) maxWidth else minWidth)


        Button(onClick = { unfold = !unfold }) {
            Text(text = "显示动画")
        }


        Row(
            modifier = Modifier
                .height(height),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColumnItem(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(10.dp))
            ColumnItem(verticalArrangement = Arrangement.SpaceBetween)
            Spacer(modifier = Modifier.width(10.dp))
            ColumnItem(verticalArrangement = Arrangement.SpaceAround)
            Spacer(modifier = Modifier.width(10.dp))

            ColumnItem(verticalArrangement = Arrangement.SpaceEvenly)
            Spacer(modifier = Modifier.width(10.dp))

            ColumnItem(verticalArrangement = Arrangement.Top)
            Spacer(modifier = Modifier.width(10.dp))

            ColumnItem(verticalArrangement = Arrangement.Center)
            Spacer(modifier = Modifier.width(10.dp))

            ColumnItem(verticalArrangement = Arrangement.Bottom)
        }
    }
}

@Composable
fun ColumnItem(
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = verticalArrangement,
        modifier = Modifier
            .fillMaxHeight()
            .background(
                Color.Gray
            )
    ) {
        Item(title = "A", modifier = modifier)
        Item(title = "B", modifier = modifier)
        Item(title = "C", modifier = modifier)
    }
}

@Composable
fun BoxItem(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .height(20.dp)
            .defaultMinSize(minWidth = 20.dp, minHeight = 20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)

    ) {
        Text(
            text = title, modifier = Modifier
                .align(Alignment.Center)
        )
    }
}


@Composable
fun SpacerBase() {
    Spacer(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
    )
}

/**
 * 【reverseLayout】：是否反向布局【Boolean】
 * 【content】：【LazyListScope.() -> Unit】
 */
@Composable
fun LazyColumnBase() {
    Box(
        modifier = Modifier
            .height(100.dp)
    ) {
        LazyColumn() {
            // Add a single item
            item {
                Text(text = "First item")
            }
            // Add 5 items
            items(50) { index ->
                Text(text = "Item: $index")
            }
            // Add another single item
            item {
                Text(text = "Last item")
            }
        }
    }
}

/**
 * 添加key之后，当数据集发生改变之后，可以帮助Compose正确的排序，减少不必要的重组。
 */
@Composable
fun LazyColumnWithKey() {
    val list = List(50) { index -> "Item: $index" }
    Box(
        modifier = Modifier
            .height(100.dp)
    ) {
        LazyColumn {
            items(items = list, key = { value ->
                value
            }) { value ->
                Text(text = value)
            }
        }
    }
}


/**
 * 【reverseLayout】：是否反向布局【Boolean】
 * 【content】：【LazyListScope.() -> Unit】
 */
@Composable
fun LazyRowBase() {
    Box {
        LazyRow() {
            // Add a single item
            item {
                Text(text = "First item")
            }
            // Add 5 items
            items(50) { index ->
                Text(text = "Item: $index")
            }
            // Add another single item
            item {
                Text(text = "Last item")
            }
        }
    }
}

/**
 * 添加key之后，当数据集发生改变之后，可以帮助Compose正确的排序，减少不必要的重组。
 */
@Composable
fun LazyRowWithKey() {
    val list = List(50) { index -> "Item: $index" }
    Box {
        LazyRow {
            items(items = list, key = { value ->
                value
            }) { value ->
                Text(text = value)
            }
        }
    }
}

/**
 * 【cells】：网格排列方式【GridCells】
 *  GridCells.Adaptive(128.dp)代表每个Cell 占128.dp
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalGridBase() {
    val list = (1..20).map { it.toString() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    Card(
                        backgroundColor = Color.Red,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 8.dp,
                    ) {
                        Text(
                            text = list[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        )
    }
}


/**
 * 【cells】：网格排列方式,GridCells.Fixed()每行cell数固定【GridCells】
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyVerticalGridFixed() {
    val list = (1..20).map { it.toString() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    Card(
                        backgroundColor = Color.Red,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        elevation = 8.dp,
                    ) {
                        Text(
                            text = list[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemBase() {
    ListItem(
        icon = {
            Icon(
                Icons.Outlined.Create,
                contentDescription = "",
            )
        },
        secondaryText = {
            Text(text = "secondaryTextsecondaryTextsecondaryTextsecondaryTextsecondaryTextsecondaryTextsecondaryText")
        },
        singleLineSecondaryText = false,
        overlineText = { Text(text = "overlineText") },
        trailing = { Text(text = "trailing") },
        modifier = Modifier
            .clickable { }
            .background(Color.Blue)

    ) {
        Text(text = "关于应用")
    }
}

@Preview
@Composable
fun Preview() {
    RowArrangement()

}

@Preview
@Composable
fun ColumnPreview() {
    ColumnArrangement()
}