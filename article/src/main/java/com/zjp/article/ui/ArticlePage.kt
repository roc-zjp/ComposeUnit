package com.zjp.article.ui

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.zjp.article.repository.LeaderBlog
import com.zjp.article.viewmodel.ArticleViewModel
import com.zjp.common.Const
import com.zjp.common.R
import com.zjp.common.compose.RefreshLayout
import com.zjp.common.compose.WrapLayout
import com.zjp.core_net.ArticleBean
import com.zjp.core_net.Banner
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import kotlin.math.absoluteValue


@Composable
fun ArticlePage(
    viewModel: ArticleViewModel = viewModel(),
    navigationToWebView: (base64Url: String, title: String) -> Unit
) {
    if (viewModel.articles.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize())
    } else {
        RefreshLayout(
            onRefresh = {
                viewModel.refresh()
            },
            onLoadMore = {
                viewModel.loadMore()
            },
            loadEnable = viewModel.loadMoreAble,
            loading = viewModel.loading,
            modifier = Modifier
                .padding(bottom = com.zjp.common.shape.AppBarHeight)
                .navigationBarsPadding()
        ) { top, bottom ->
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(
                    top = top,
                    bottom = bottom
                ),
                state = rememberLazyListState()
            ) {
                item {
                    BannerHeader(viewModel.banners, 0f, navigationToWebView)
                }
                item {
                    LeaderBlogItem(
                        blogs = viewModel.blogs,
                        navigationWebView = navigationToWebView
                    )
                }
                items(viewModel.articles) { item ->
                    ArticleItem(articleBean = item, navigationToWebView = navigationToWebView)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleItem(articleBean: ArticleBean, navigationToWebView: (String, String) -> Unit) {
    Column {
        ListItem(modifier = Modifier
            .clickable {
                navigationToWebView(
                    Base64.encodeToString(
                        articleBean.link.toByteArray(),
                        Base64.DEFAULT
                    ),
                    dealTitle(articleBean.title)
                )
            }
            .padding(top = 5.dp, bottom = 5.dp), secondaryText = {
            Text(
                text = dealInfo(articleBean),
                modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
            )
        }) {
            Text(text = dealTitle(articleBean.title), fontSize = 20.sp)
        }
        Divider()
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerHeader(
    banners: List<Banner>,
    progress: Float,
    navigationToWebView: (base64Url: String, title: String) -> Unit
) {
    if (banners.isEmpty()) {

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
                delay(3000)
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
                    .clickable {
                        navigationToWebView(
                            Base64.encodeToString(
                                banners[page % banners.size].url.toByteArray(),
                                Base64.DEFAULT
                            ),
                            dealTitle(banners[page % banners.size].title)
                        )
                    }

            ) {
                AsyncImage(
                    model = banners[page % banners.size].imagePath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }


        }

    }
}

@Composable
fun LeaderBlogItem(
    blogs: List<LeaderBlog>?,
    navigationWebView: (base64Url: String, title: String) -> Unit
) {
    Column {

        Row(
            modifier = Modifier.padding(
                top = 10.dp, bottom = 10.dp, start = 10.dp
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.link),
                contentDescription = "",
                tint = Const.colorDarkBlue
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(
                text = "大佬博客",
                style = TextStyle(fontWeight = FontWeight.Black, fontSize = 18.sp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        if (blogs != null && blogs.isNotEmpty()) {
            WrapLayout(
                Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                blogs.forEach { blog ->
                    Button(
                        onClick = {
                            navigationWebView(
                                Base64.encodeToString(
                                    blog.blogUrl.toByteArray(),
                                    Base64.DEFAULT
                                ),
                                dealTitle(blog.name)
                            )
                        },
                        shape = CircleShape,
                        modifier = Modifier.padding(
                            start = 2.dp,
                            end = 2.dp,
                        )
                    ) {
                        Text(text = blog.name)
                    }
                }
            }
        } else {
            Button(
                onClick = { },
                enabled = false,
                shape = CircleShape,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Text(text = "暂无链接组合")
            }
        }

        Divider()
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



