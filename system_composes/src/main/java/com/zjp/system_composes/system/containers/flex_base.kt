package com.zjp.system_composes.system.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    var list = List(50) { index -> "Item: $index" }
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
    var list = List(50) { index -> "Item: $index" }
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


@Preview
@Composable
fun Preview() {
    LazyColumnBase()
}