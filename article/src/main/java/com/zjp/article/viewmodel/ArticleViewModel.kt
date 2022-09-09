package com.zjp.article.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.article.repository.LeaderBlog
import com.zjp.article.repository.LeaderBlogRepository
import com.zjp.core_net.ArticleBean
import com.zjp.core_net.Banner
import com.zjp.core_net.WanAndroidNetWork
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    var articles by mutableStateOf(ArrayList<ArticleBean>())

    var blogs by mutableStateOf(listOf<LeaderBlog>())

    var banners by mutableStateOf(emptyList<Banner>())

    val blogRepository = LeaderBlogRepository()

    private val wanAndroidNetWork = WanAndroidNetWork()

    init {
        viewModelScope.launch {
            val bean = wanAndroidNetWork.searchArticle("0")
            articles = bean?.data?.datas ?: ArrayList()
        }

        viewModelScope.launch {
            val bean = wanAndroidNetWork.getBanner()
            banners = bean?.data ?: emptyList()
        }

        blogs = blogRepository.getLeadersBlog()

    }
}