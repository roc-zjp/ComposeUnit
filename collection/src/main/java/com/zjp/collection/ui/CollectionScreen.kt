package com.zjp.collection.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.collection.R
import com.zjp.collection.viewmodel.CollectionViewModel
import com.zjp.common.LocalFont
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.FoldAppbar
import com.zjp.common.state.CommonUiState
import com.zjp.core_database.model.Compose
import okhttp3.internal.wait

@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = viewModel(),
    onClick: (compose: Compose) -> Unit,
) {
    Scaffold(topBar = {

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
            val collections = (uiState as CommonUiState.HasData<List<Compose>>).data

            FoldAppbar(
                minHeightPx = with(LocalDensity.current) { 80.dp.roundToPx().toFloat() },
                maxHeightPx = with(LocalDensity.current) { 200.dp.roundToPx().toFloat() },
                appBar = { min, max, progress ->
                    Box(
                        modifier = Modifier
                            .height(with(LocalDensity.current) { (max - (max - min) * progress).toDp() })
                            .fillMaxWidth()
                    ) {

                        val color = LocalThemeColor.current
                        Image(
                            painter = painterResource(id = com.zjp.common.R.drawable.caver),
                            contentDescription = "caver",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color.copy(alpha = progress))
                        )
                        CollectionTitle(alpha = progress)
                    }
                },
            ) { innerPadding, scrollState ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(top = with(LocalDensity.current) { innerPadding.toDp() }),
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {
                        items(collections) { item ->
                            CollectionItem(item = item, onClick)
                        }
                    },
                    state = scrollState
                )
            }
        }
    }
}


@Composable
fun CollectionTitle(alpha: Float = 1f) {
    ProvideWindowInsets() {
        val sbPaddingValues = rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars)
        Box(
            modifier = Modifier
                .padding(sbPaddingValues)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = com.zjp.common.R.drawable.kobe),
                contentScale = ContentScale.Crop,
                contentDescription = "kobe",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, color = Color.White, shape = CircleShape)
            )

            Text(
                text = "收藏集",
                color = Color.White.copy(alpha = alpha),
                modifier = Modifier
                    .align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = LocalFont.current
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionItem(item: Compose, onClick: (compose: Compose) -> Unit) {
    Card(modifier = Modifier.padding(10.dp), onClick = {
        onClick(item)
        LogUtils.d("onclick")
    }) {
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


@Preview
@Composable
fun TitlePre() {
    CollectionTitle()
}


