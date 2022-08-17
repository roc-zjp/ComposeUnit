package com.zjp.collection.ui

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zjp.collection.CollectionNodeMap
import com.zjp.collection.viewmodel.CollectionDetailViewModel
import com.zjp.collection.viewmodel.DetailViewModelFactory
import com.zjp.common.code.CodeView
import com.zjp.common.compose.*
import com.zjp.core_database.model.CollectionNode

@Composable
fun CollectionDetailPage(
    composeId: Int = -1,
    viewModel: CollectionDetailViewModel = viewModel(factory = DetailViewModelFactory(composeId)),
    goBack: () -> Unit = {},
    goHome: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            UnitTopAppBar(
                title = { viewModel.compose?.name?.let { Text(text = it) } },
                navigationIcon = {
                    IconButton(onClick = {
                        goBack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        goHome()
                    }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }

                    IconButton(onClick = {
                        viewModel.toggleLike()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Home",
                            tint = if (viewModel.likeStatus) Color.Red else Color.Gray
                        )
                    }

                },
            )
        }) {


        BoxWithConstraints(modifier = Modifier.padding(it)) {

            FoldAppbar(
                minHeightDp = 0.dp,
                maxHeightDp = maxWidth / 2,
                appBar = {
                    if (viewModel.compose != null) {
                        Image(
                            bitmap = base64ToBitmap(viewModel.compose!!.img),
                            contentDescription = "title",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }) {
                item {
                    ComposeHeadView(viewModel.compose?.name, viewModel.compose?.info)
                }
                if (viewModel.nodes.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.Gray)
                                .height(500.dp)
                        ) {
                            Text(text = "待收录", modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else {
                    items(viewModel.nodes) { node: CollectionNode ->
                        ComposeNode(node = node)
                    }
                }
            }

            AnimatedVisibility(
                visible = viewModel.tips,
                modifier = Modifier.align(Alignment.BottomStart),
                enter = slideInVertically(initialOffsetY = {
                    30.dp.value.toInt()
                }),
                exit = slideOutVertically(
                    targetOffsetY = { 30.dp.value.toInt() }
                )
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                ) {
                    Text(
                        text = if (viewModel.likeStatus) "收藏成功" else "取消成功",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ComposeNode(node: CollectionNode) {
    var folded by remember {
        mutableStateOf(true)
    }
    Column {
        NodeTitle(title = node.name, folded = folded) {
            folded = !folded
        }
        AnimatedContent(targetState = folded) {
            if (!it) {
                CodeView(
                    code = node.code,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(10.dp)
                .heightIn(20.dp, 500.dp)
        ) {

            CollectionNodeMap(id = node.id!!)

        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color(0xffF6F8FA))
        ) {
            Text(
                text = node.subtitle,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
        }

    }
}


fun base64ToBitmap(base64: String): ImageBitmap {
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return bitmap.asImageBitmap()
}


@Preview
@Composable
fun ItemPre() {

}
