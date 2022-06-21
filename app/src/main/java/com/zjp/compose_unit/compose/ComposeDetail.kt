package com.zjp.compose_unit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zjp.compose_unit.code.CodeView
import com.zjp.compose_unit.common.Screen
import com.zjp.compose_unit.common.compose.ComposeHeadView
import com.zjp.compose_unit.common.compose.DividerView
import com.zjp.compose_unit.common.viewmodel.DbViewModel
import com.zjp.compose_unit.common.viewmodel.ShareViewModel
import com.zjp.compose_unit.nodeMap
import com.zjp.core_database.model.Node


@Composable
fun ComposeDetailPage(
    viewModel: ShareViewModel,
    dbViewModel: DbViewModel,
    navController: NavController
) {
    val mList by dbViewModel.nodes.observeAsState()
    val like by dbViewModel.like.observeAsState()
    dbViewModel.getNodesByWeightId(viewModel.compose!!.id!!)
    dbViewModel.likeStatus(viewModel.compose!!.id!!)
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
                    dbViewModel.like(viewModel.compose!!.id!!)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Home",
                        tint = if (like == true) Color.Red else Color.Gray
                    )
                }

            }
        )
    }) {
        var scrollState = rememberScrollState()

        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)

        ) {
            ComposeHeadView(viewModel.compose!!.nameCN, viewModel.compose!!.info)
            mList?.mapIndexed { _, node ->
                ComposeNode(node = node)
            }?.toList()
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

