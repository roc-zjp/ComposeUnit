package com.zjp.core_net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.Serializable


interface WanAndroidApi {
    @POST("/article/query/{pageNo}/json")
    @FormUrlEncoded
    suspend fun searchArticle(
        @Path("pageNo") pageNo: String,
        @Field("k") k: String = "compose",
        @Field("page_size") page_size: Int = 40
    ): NetworkResponse<DataBean<ArticleBean>>?


    @GET("/banner/json")
    suspend fun banner(): NetworkResponse<List<Banner>>?
}

class WanAndroidNetWork {
    companion object {
        const val UnitBaseUrl = "https://www.wanandroid.com"
    }

    private val networkApi = Retrofit.Builder()
        .baseUrl(UnitBaseUrl)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(WanAndroidApi::class.java)

    suspend fun searchArticle(pageNo: Int): NetworkResponse<DataBean<ArticleBean>>? {
        return networkApi.searchArticle("$pageNo")
    }

    suspend fun getBanner(): NetworkResponse<List<Banner>>? {
        return networkApi.banner()
    }

}


data class NetworkResponse<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String,
) : Serializable {
    constructor() : this(null, 0, "")
}

data class DataBean<T>(
    val curPage: Int = 0,
    val datas: ArrayList<T>? = null,
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 11,
    val size: Int = 0,
    val total: Int = 0
) {
    constructor() : this(0)
}


data class Banner(
    val desc: String = "",
    val id: Int = 0,
    val imagePath: String = "",
    val isVisible: Int = 0,
    val order: Int = 0,
    val title: String = "",
    val type: Int = 0,
    val url: String = ""
)


data class ArticleBean(
    val shareDate: Long = 0,
    val projectLink: String = "",
    val prefix: String = "",
    val canEdit: Boolean = false,
    val origin: String = "",
    val link: String = "",
    val title: String = "",
    val type: Int = 0,
    val selfVisible: Int = 0,
    val apkLink: String = "",
    val envelopePic: String = "",
    val audit: Int = 0,
    val chapterId: Int = 0,
    val host: String = "",
    val realSuperChapterId: Int = 0,
    val id: Int = 0,
    val courseId: Int = 0,
    val superChapterName: String = "",
    val descMd: String = "",
    val publishTime: Long = 0,
    val niceShareDate: String = "",
    val visible: Int = 0,
    val niceDate: String = "",
    val author: String = "",
    val zan: Int = 0,
    val chapterName: String = "",
    val userId: Int = 0,
    val adminAdd: Boolean = false,
    val isAdminAdd: Boolean = false,
    val superChapterId: Int = 0,
    val fresh: Boolean = false,
    val collect: Boolean = false,
    val shareUser: String = "",
    val desc: String = ""
) : Serializable {
    constructor() : this(0, "")
}