package com.zjp.collection.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Guideline
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


//            Scaffold() {
//                BoxWithConstraints(modifier = Modifier.padding(it)) {
//
//                }
//            }

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

//    Column {
//        Row(
//            modifier = Modifier
//                .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
//                .height(163.dp)
//                .fillMaxWidth()
//                .clickable { onClick(item) },
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.images),
//                contentScale = ContentScale.Crop,
//                contentDescription = "kobe",
//                modifier = Modifier
//                    .width(120.dp)
//                    .height(60.dp)
//                    .align(Alignment.CenterVertically)
//                    .clip(RoundedCornerShape(5.dp))
//
//            )
//            Spacer(modifier = Modifier.width(10.dp))
//            Column {
//                Text(text = item.name, fontSize = 25.sp, color = Color(0xFF482BE7))
//                Text(
//                    text = item.info,
//                    fontSize = 20.sp,
//                    color = Color(0xFFBFBFBF),
//                    maxLines = 3,
//                )
//            }
//
//        }
//        Divider(
//            modifier = Modifier
//                .height(0.5.dp)
//                .padding(start = 15.dp, end = 15.dp)
//                .background(Color(0xFFDDDDDD))
//        )
//    }

    Card(
        modifier = Modifier.padding(15.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(item) }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.images),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)

            )

            Box(modifier = Modifier.padding(16.dp)) {
                Column() {
                    Text(
                        text = item.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.info,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        }
    }

}


@Preview(widthDp = 360)
@Composable
fun TitlePre() {
    CollectionItem(item = Compose(1, "name", "nameCn", 1, 1, 3.0f, "s", "info"), onClick = {})
}



