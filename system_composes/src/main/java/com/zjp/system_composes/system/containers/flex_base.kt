package com.zjp.system_composes.system.containers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zjp.system_composes.R

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
            cells = GridCells.Adaptive(128.dp),
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
            cells = GridCells.Fixed(2),
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
            modifier = Modifier.clickable { }.background(Color.Blue)

        ) {
            Text(text = "关于应用")
        }
}

@Preview
@Composable
fun Preview() {
    LazyColumnBase()
}