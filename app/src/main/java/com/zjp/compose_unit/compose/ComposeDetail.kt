package com.zjp.compose_unit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.apkfuns.logutils.LogUtils
import com.zjp.compose_unit.DetailViewModel
import com.zjp.compose_unit.DetailViewModelFactory
import com.zjp.compose_unit.code.CodeView
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.compose.ComposeHeadView
import com.zjp.compose_unit.common.compose.DividerView
import com.zjp.compose_unit.nodeMap
import com.zjp.core_database.model.Node


@Composable
fun ComposeDetailPage(
    navController: NavController,
    composeId: Int = -1,
    viewModel: DetailViewModel = viewModel(factory = DetailViewModelFactory(composeId))
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { viewModel.compose?.name?.let { Text(text = it) } },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.popBackStack(Screen.Main.route, false)
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

            }
        )

    }) {
        LaunchedEffect(true) {
//            viewModel.first()
        }
        Box(modifier = Modifier.padding(it)) {
            var scrollState = rememberScrollState()
            Column(
                Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom = 20.dp)

            ) {
                ComposeHeadView(viewModel.compose)
                if (viewModel.nodes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Red)
                            .height(500.dp)
                    ) {
                        Text(text = "待收录", modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    viewModel.nodes!!.mapIndexed { _, node ->
                        ComposeNode(node = node)
                    }?.toList()
                }
            }
//            LogUtils.d(viewModel.composeId)
//            Text(text = "detail")
        }
    }
}

@Composable
fun ComposeNode(node: Node) {
    Column() {
        DividerView(title = node.name)
        CodeView(
            code = node.code,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        )
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
            nodeMap(id = node.id!!)
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(Color(0xfffafafa))
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

