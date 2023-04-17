package com.zjp.core_net

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.io.Serializable

data class FileBean(
    val version: Int = 0,
    val id: Int = 0,
    val filePath: String = "",
    val md5: String = "",
    val fileName: String = "",
    val fileType: String = "",
    val updatedDateTime: String = "",//2023-04-15T22:42:00.316902

)

data class FileResponse<T>(
    val data: T?,
    val code: Int,
    val msg: String,
) : Serializable {
    constructor() : this(null, 0, "")
}

interface FileApi {
    @GET("/file/query")
    suspend fun searchFirmwark(
        @Query("appType") appType: String,
    ): FileResponse<FileBean>?

}

class FileNetWork {
    companion object {
        const val LoalHost = "http://192.168.10.41:10000"
    }

    private val networkApi = Retrofit.Builder()
        .baseUrl(LoalHost)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(FileApi::class.java)


    suspend fun searchArticle(appType: String): FileResponse<FileBean>? {
        return networkApi.searchFirmwark(appType);
    }
}