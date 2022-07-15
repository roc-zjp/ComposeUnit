package com.zjp.compose_unit.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zjp.system_composes.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionPage() {
    Scaffold(topBar = {
        Image(
            painter = painterResource(id = R.drawable.caver),
            contentScale = ContentScale.Crop,
            contentDescription = "Header",
            modifier = Modifier
                .height(
                    200.dp
                )
                .fillMaxWidth()
        )
    }) {
        val items = List(3) { index ->
            "item$index"
        }
        LazyVerticalGrid(cells = GridCells.Fixed(count = 2), content = {
            items(items) {
                CollectionItem()
            }
        })
    }
}


@Composable
fun CollectionItem() {
    Card(modifier = Modifier.padding(10.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .width(30.dp)
                        .height(30.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .border(5.dp, color = Color.White, CircleShape)


                ) {
                    Text(text = "18", modifier = Modifier.align(Alignment.Center))
                }
                Text(text = "Side effects", modifier = Modifier.weight(1f))
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
            Divider()
            Box(
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "收集常用的Side effects,为日常使用提供参考")
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "edit",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 5.dp, end = 5.dp)
                )
            }
            Divider()
            Text(
                text = "创建于10291291-12121",
                modifier = Modifier
                    .height(30.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ItemPre() {
    CollectionItem()
}

