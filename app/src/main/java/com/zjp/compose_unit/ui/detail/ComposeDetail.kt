package com.zjp.compose_unit.ui.detail

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zjp.common.Const
import com.zjp.common.code.CodeView
import com.zjp.common.compose.ComposeHeadView
import com.zjp.common.compose.NodeTitle
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.common.compose.WrapLayout
import com.zjp.compose_unit.viewmodel.DetailViewModel
import com.zjp.compose_unit.viewmodel.DetailViewModelFactory
import com.zjp.core_database.model.Compose
import com.zjp.core_database.model.Node
import com.zjp.system_composes.NodeMap

@Composable
fun ComposeDetailPage(
    composeId: Int = -1,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModelFactory(composeId)),
    goBack: () -> Unit = {},
    goHome: () -> Unit = {},
    toComposeDetail: (id: Int) -> Unit = {}
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            LazyColumn(
                contentPadding = WindowInsets.ime.only(WindowInsetsSides.Vertical + WindowInsetsSides.Bottom)
                    .union(
                        WindowInsets.navigationBars
                    )
                    .asPaddingValues()
            ) {
                item {
                    ComposeHeadView(viewModel.compose?.name, viewModel.compose?.info)
                    LinkComposes(links = viewModel.links, onItemClick = { id ->
                        toComposeDetail(id)
                    })
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
                    items(viewModel.nodes) { node: Node ->
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
fun ComposeNode(node: Node) {
    var folded by remember {
        mutableStateOf(true)
    }
    Column() {
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
                .fillMaxSize()
        ) {
            NodeMap(id = node.id!!)
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


@Composable
fun LinkComposes(links: List<Compose>?, onItemClick: (id: Int) -> Unit) {
    Column {
        Divider()
        Row(
            modifier = Modifier.padding(
                top = 10.dp, bottom = 10.dp, start = 10.dp
            )
        ) {
            Icon(
                painter = painterResource(id = com.zjp.common.R.drawable.link),
                contentDescription = "",
                tint = Const.colorDarkBlue
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                text = "相关组合",
                style = TextStyle(fontWeight = FontWeight.Black, fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        if (links != null && links.isNotEmpty()) {
            WrapLayout(
                Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                links.forEach { compose ->
                    Button(
                        onClick = { onItemClick(compose.id) },
                        shape = CircleShape,
                        modifier = Modifier.padding(
                            start = 2.dp,
                            end = 2.dp,
                        )
                    ) {
                        Text(text = compose.name)
                    }
                }
            }
        } else {
            Button(
                onClick = { },
                enabled = false,
                shape = CircleShape,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(text = "暂无链接组合")
            }
        }
    }
}

@Preview
@Composable
fun ItemPre() {

}

