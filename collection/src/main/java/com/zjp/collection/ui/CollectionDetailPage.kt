package com.zjp.collection.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.zjp.collection.CollectionNodeMap
import com.zjp.collection.viewmodel.CollectionDetailViewModel
import com.zjp.collection.viewmodel.DetailViewModelFactory
import com.zjp.common.LocalThemeColor
import com.zjp.common.R
import com.zjp.common.code.CodeView
import com.zjp.common.compose.NodeTitle
import com.zjp.common.utils.base64ToBitmap
import com.zjp.core_database.model.CollectionNode

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CollectionDetailPage(
    composeId: Int = -1,
    viewModel: CollectionDetailViewModel = viewModel(factory = DetailViewModelFactory(composeId)),
    goBack: () -> Unit = {},
    goHome: () -> Unit = {},
) {
    val nodes = viewModel.nodes.toMutableStateList()
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (viewModel.compose != null) {
                    Image(
                        bitmap = base64ToBitmap(viewModel.compose!!.img),
                        contentDescription = "title",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    ProvideWindowInsets {
                        val color = LocalThemeColor.current
                        Row(
                            modifier = Modifier
                                .background(color.copy(alpha = 0.3f))
                                .statusBarsPadding()
                        ) {
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = viewModel.compose!!.name,
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                goHome()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = "Home"
                                )
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
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }

        }) {

        BoxWithConstraints(modifier = Modifier.padding(it)) {

            if (nodes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray)

                ) {
                    Text(text = "待收录", modifier = Modifier.align(Alignment.Center))
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    HorizontalPager(count = nodes.size, modifier = Modifier.fillMaxSize()) { page ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            ComposeNode(node = nodes[page])
                        }
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
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                .fillMaxWidth()
                .border(0.5.dp, Color.Black)
                .clipToBounds()
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                CollectionNodeMap(id = node.id!!)
            }
            Button(
                onClick = { },
                enabled = false,
                shape = CircleShape,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(text = "示例")
            }
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
fun InfoHeader(name: String, info: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp)

        ) {
            Text(text = info, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
        Image(
            painter = painterResource(id = R.drawable.caver),
            contentDescription = null,
            modifier = Modifier
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .width(100.dp)
                .height(66.dp)
        )
    }
}


@Preview
@Composable
fun ItemPre() {

}
