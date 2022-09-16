package com.zjp.article.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import com.zjp.article.repository.LeaderBlog
import com.zjp.article.repository.LeaderBlogRepository
import com.zjp.core_net.ArticleBean
import com.zjp.core_net.Banner
import com.zjp.core_net.WanAndroidNetWork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    var articles = mutableStateListOf<ArticleBean>()

    var blogs by mutableStateOf(emptyList<LeaderBlog>())

    var banners by mutableStateOf(emptyList<Banner>())

    val blogRepository = LeaderBlogRepository()

    private val wanAndroidNetWork = WanAndroidNetWork()

    private var currentPageNo = 0

    var loading by mutableStateOf(false)

    init {
        LogUtils.d("init")
        viewModelScope.launch {
            val bean = wanAndroidNetWork.searchArticle(currentPageNo)
            bean?.data?.datas?.let { articles.addAll(it) }
        }
        viewModelScope.launch {
            val bean = wanAndroidNetWork.getBanner()
            banners = bean?.data ?: emptyList()
        }
        blogs = blogRepository.getLeadersBlog()
    }


    fun loadMore() {
        currentPageNo++
        loading = true
        viewModelScope.launch {
            delay(3000)
            val bean = wanAndroidNetWork.searchArticle(currentPageNo)
            bean?.data?.datas?.let { articles.addAll(it) }
            loading = false
        }
    }

    fun refresh() {
        currentPageNo = 0
        loading = true
        viewModelScope.launch {
            delay(3000)
            val bean = wanAndroidNetWork.searchArticle(currentPageNo)
            articles.clear()
            bean?.data?.datas?.let { articles.addAll(it) }
            loading = false
        }
    }

}