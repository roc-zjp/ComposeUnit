package com.zjp.collection.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zjp.collection.viewmodel.CollectionViewModel
import com.zjp.common.compose.UnitTopAppBar
import com.zjp.common.state.CommonUiState
import com.zjp.core_database.model.Collection

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectionScreen(viewModel: CollectionViewModel = viewModel()) {
    Scaffold(topBar = {
        UnitTopAppBar(title = {
            Text(text = "收藏集")
        })
    }) { innerPadding ->
        var uiState = viewModel.uiState

        if (uiState is CommonUiState.NoData) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else if (uiState.errorMessages.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Text(text = uiState.errorMessages.first()!!, color = Color.Red)
                }
            }
        } else {
            var collections = (uiState as CommonUiState.HasData<List<Collection>>).data

            LazyVerticalGrid(modifier = Modifier.padding(innerPadding),
                cells = GridCells.Fixed(2), content = {
                    items(collections) { item ->
                        CollectionItem(item = item)
                    }
                })
        }

    }
}


@Composable
fun CollectionItem(item: Collection) {
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
                Text(text = item.name, modifier = Modifier.weight(1f))
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }
            Divider()
            Box(
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Text(text = item.info)
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


