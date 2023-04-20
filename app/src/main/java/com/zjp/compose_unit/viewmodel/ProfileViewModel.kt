package com.zjp.compose_unit.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.core_database.DBManager
import com.zjp.core_net.FileBean
import com.zjp.core_net.FileNetWork
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var hasAppUpdate by mutableStateOf(false)
    var appNewVersion by mutableStateOf<FileBean?>(null)
    var sqliteNewVersion by mutableStateOf<FileBean?>(null)
    private val fileNetwork = FileNetWork()
    var message by mutableStateOf("")

    fun checkAppUpdate(context: Context) {
        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        viewModelScope.launch {
            try {
                val bean = fileNetwork.searchArticle("compose_app")
                bean?.data?.let {
                    if (it.version >= version) {
                        appNewVersion = it
                        message = "获取到了App新版本"
                        return@launch
                    }
                }
                message = "App已经是最新版本"
                appNewVersion = null
            } catch (e: Exception) {
                message = "网络请求失败"
                e.printStackTrace()
            }
        }
    }

    fun checkDatabaseUpdate() {
        val version = DBManager.getInstance().mDB.version
        viewModelScope.launch {
            try {
                val bean = fileNetwork.searchArticle("sqlite")
                bean?.data?.let {
                    if (it.version >= version) {
                        sqliteNewVersion = it
                        message = "获取到了数据库新版本"
                        return@launch
                    }
                }
                sqliteNewVersion = null
                message = "数据库已经是最新版本"
            } catch (e: Exception) {
                e.printStackTrace()
                message = "网络请求失败"
            }
        }
    }


}