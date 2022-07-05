package com.zjp.compose_unit.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.zjp.compose_unit.ui.detail.code.CodeView
import com.zjp.compose_unit.route.Screen
import com.zjp.compose_unit.ui.home.ComposeHeadView
import com.zjp.compose_unit.viewmodel.DetailViewModel
import com.zjp.compose_unit.viewmodel.DetailViewModelFactory
import com.zjp.core_database.model.Node
import com.zjp.system_composes.NodeMap


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
        Box(modifier = Modifier.padding(it)) {
            val scrollState = rememberScrollState()
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
                            .background(color = Color.Gray)
                            .height(500.dp)
                    ) {
                        Text(text = "待收录", modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    viewModel.nodes.mapIndexed { _, node ->
                        ComposeNode(node = node)
                    }.toList()
                }
            }
        }
    }
}

@Composable
fun ComposeNode(node: Node) {
    var folded by remember {
        mutableStateOf(true)
    }
    Column() {
        NodeTitle(title = node.name, folded = folded) {
            folded = !folded
        }
        if (!folded) {
            CodeView(
                code = node.code,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
        Box(modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
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

