package com.zjp.compose_unit.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apkfuns.logutils.LogUtils
import com.zjp.compose_unit.common.Const
import com.zjp.compose_unit.common.shape.TechnoInlineShape
import com.zjp.compose_unit.common.shape.TechnoOutlineShape
import com.zjp.compose_unit.common.shape.TechnoShapeBorder
import com.zjp.compose_unit.viewmodel.HomeUiState
import com.zjp.compose_unit.viewmodel.HomeViewModel
import com.zjp.core_database.model.Compose

@Composable
fun ComposesScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onClick: (compose: Compose) -> Unit,
) {
    val uiState = homeViewModel.uiState
    LogUtils.d("重组")
    if (homeViewModel.uiState.isLoading) {
        LoadingCompose()
    } else {
        if (uiState is HomeUiState.HasCompose) {
            Composes(uiState.composes, onClick)
        } else {
            NoCompose()
        }
    }
}


@Composable
fun Composes(composes: List<Compose>, onClick: (compose: Compose) -> Unit) {
    LazyColumn() {
        items<Compose>(
            composes,
            key = { it.id }) {
            ComposeItemView(compose = it) {
                onClick(it)
            }
        }
    }
}


@Composable
fun NoCompose() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "没数据，哥也没办法\\n(≡ _ ≡)/~┴┴", modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
fun LoadingCompose() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "加载中", modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
fun ComposeItemView(
    compose: Compose,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(TechnoShapeBorder(storkWidth = 1.0f, cornerWidth = 20.dp.value))
            .border(
                1.dp,
                Const.colorDarkBlue,
                TechnoShapeBorder(storkWidth = 1.0f, cornerWidth = 20.dp.value)
            )
            .background(Const.colorDarkBlue.copy(0.25f))
            .clickable { onClick() }
            .padding(10.dp)
    ) {

        Box(
            modifier = Modifier
                .size(80.dp, 80.dp)
                .border(3.dp, Color.White, CircleShape), contentAlignment = Alignment.Center
        ) {
            Text(
                text = compose.name.substring(IntRange(0, 1)),
                style = TextStyle(
                    fontSize = 28.sp,
                    textAlign = TextAlign.End,
                    color = Const.colorDarkBlue,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(5f, 5f),
                        blurRadius = 5f
                    )
                ),
            )
        }
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = compose.name,
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = compose.info,
                style = TextStyle(color = Color.Gray),
                maxLines = 2,
                color = Color(0xFF757575),
                fontSize = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun DefaultPre() {
    ComposeItemView(compose = Compose(1, "Text", "Text", 1, 1, 3.0f, "", "info")) {

    }
}