package com.zjp.article.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import com.zjp.article.repository.LeaderBlog
import com.zjp.article.repository.LeaderBlogRepository
import com.zjp.core_net.ArticleBean
import com.zjp.core_net.Banner
import com.zjp.core_net.WanAndroidNetWork
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    var articles = mutableStateListOf<ArticleBean>()

    var blogs by mutableStateOf(emptyList<LeaderBlog>())

    var banners by mutableStateOf(emptyList<Banner>())

    private val blogRepository = LeaderBlogRepository()

    private val wanAndroidNetWork = WanAndroidNetWork()

    private var currentPageNo = -1

    var loading by mutableStateOf(false)

    var loadMoreAble by mutableStateOf(true)

    init {
        LogUtils.d("init")
        viewModelScope.launch {
            loadMore()
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
            val bean = wanAndroidNetWork.searchArticle(currentPageNo)
            bean?.data?.datas?.let { articles.addAll(it) }
            bean?.data?.over?.let {
                loadMoreAble = !it
            }
            loading = false
        }
    }

    fun refresh() {
        loadMoreAble = true
        currentPageNo = -1
        loading = true
        viewModelScope.launch {
            val bean = wanAndroidNetWork.searchArticle(currentPageNo)
            articles.clear()
            bean?.data?.datas?.let { articles.addAll(it) }
            bean?.data?.over?.let {
                loadMoreAble = !it
            }
            loading = false
        }

    }

}