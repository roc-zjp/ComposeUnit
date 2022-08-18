package com.zjp.collection.ui

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.zjp.collection.viewmodel.CollectionViewModel
import com.zjp.common.LocalFont
import com.zjp.common.LocalThemeColor
import com.zjp.common.compose.FoldAppbar
import com.zjp.common.state.CommonUiState
import com.zjp.core_database.model.Collection

@Composable
fun CollectionPage(
    viewModel: CollectionViewModel = viewModel(),
    onClick: (compose: Collection) -> Unit,
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
            val collections = (uiState as CommonUiState.HasData<List<Collection>>).data

            BoxWithConstraints() {
                val vertical = maxWidth < maxHeight
                FoldAppbar(
                    minHeightDp = 80.dp,
                    maxHeightDp = 200.dp,
                    appBar = { progress ->
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

                ) {
                    items(collections) { item ->

                        CollectionItem(item = item, isVertical = vertical, onClick)

                    }

                }
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


@Composable
fun VerItem() {

}

@Composable
fun HorItem() {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectionItem(item: Collection, isVertical: Boolean, onClick: (compose: Collection) -> Unit) {

    if (isVertical) {
        Card(
            modifier = Modifier.padding(15.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { onClick(item) },

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val bytes = Base64.decode(item.img, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f),
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
    } else {
        Card(
            modifier = Modifier.padding(15.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { onClick(item) }) {

            Row(modifier = Modifier.height(130.dp)) {
                val bytes = Base64.decode(item.img, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(260.dp)
                        .aspectRatio(2f)
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    Column {
                        Text(
                            text = item.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = item.info,
                            fontSize = 16.sp,
                            maxLines = 3,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }


}


@Preview()
@Composable
fun ItemVertical() {
    CollectionItem(
        item = Collection(1, "name", "nameCn", 1, 1, 3.0f, "s", "info", ""),
        isVertical = true,
        onClick = {})
}


@Preview()
@Composable
fun ItemHorizontal() {
    CollectionItem(
        item = Collection(1, "name", "nameCn", 1, 1, 3.0f, "s", "info", ""),
        isVertical = false,
        onClick = {})
}





