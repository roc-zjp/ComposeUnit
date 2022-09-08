package com.zjp.article.ui

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apkfuns.logutils.LogUtils
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.zjp.article.viewmodel.ArticleViewModel
import com.zjp.common.compose.FoldAppbar
import com.zjp.core_net.ArticleBean
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun ArticlePage(
    viewModel: ArticleViewModel = viewModel(),
    navigationToDetail: (url: String, title: String) -> Unit
) {

    if (viewModel.articles.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize())
    } else {
        val scrollState = rememberLazyListState()
        FoldAppbar(
            minHeightDp = 0.dp,
            appbar = {
                if (viewModel.banners.isEmpty()) {
                    LogUtils.d("banner isEmpty")
                    Box(
                        modifier = Modifier
                            .background(Color.Green)
                            .fillMaxSize()
                    )
                } else {
                    val pagerState = rememberPagerState(
                        initialPage = Int.MAX_VALUE / 2,
                    )
                    LaunchedEffect(key1 = pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            delay(1000)
                            pagerState.animateScrollToPage(
                                page + 1,
                                pageOffset = 0f
                            )
                        }
                    }

                    HorizontalPager(
                        count = Int.MAX_VALUE,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                        itemSpacing = 20.dp
                    ) { page ->

                        Card(
                            Modifier
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset =
                                        calculateCurrentOffsetForPage(page).absoluteValue

                                    // We animate the scaleX + scaleY, between 85% and 100%
                                    lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    ).also { scale ->
                                        scaleX = scale
                                        scaleY = scale
                                    }

                                    // We animate the alpha, between 50% and 100%
                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                        ) {
                            AsyncImage(
                                model = viewModel.banners[page % viewModel.banners.size].imagePath,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }


                    }

                }
            },
            content = { height ->
                LazyColumn(
                    contentPadding = PaddingValues(top = height),
                    state = scrollState
                ) {
                    items(viewModel.articles) { item ->
                        Column {
                            ListItem(modifier = Modifier
                                .clickable {
                                    navigationToDetail(
                                        Base64.encodeToString(
                                            item.link.toByteArray(),
                                            Base64.DEFAULT
                                        ),
                                        dealTitle(item.title)
                                    )
                                }
                                .padding(top = 5.dp, bottom = 5.dp), secondaryText = {
                                Text(
                                    text = dealInfo(item),
                                    modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
                                )
                            }) {
                                Text(text = dealTitle(item.title), fontSize = 20.sp)
                            }
                            Divider()
                        }
                    }
                }

            })
    }

}

fun dealInfo(item: ArticleBean): String {
    val buffer = StringBuffer()
    if (item.author.isNotEmpty()) {
        buffer.append("作者：${item.author}    ")
    }
    if (item.shareUser.isNotEmpty()) {
        buffer.append("分享：${item.shareUser}    ")
    }

    if (item.shareDate != 0L) {
        buffer.append("时间：${item.shareDate.formatDate()}    ")
    } else {
        buffer.append("时间：${item.publishTime.formatDate()}    ")
    }
    return buffer.toString()
}


fun Long.formatDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")
    return format.format(this)
}

fun dealTitle(title: String): String {
    return title.replace("<em class='highlight'>", "").replace("</em>", "")

}

fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}



