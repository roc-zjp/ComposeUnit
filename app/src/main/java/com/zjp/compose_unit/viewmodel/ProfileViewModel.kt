package com.zjp.compose_unit.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.core_database.DBManager
import com.zjp.core_net.FileBean
import com.zjp.core_net.FileNetWork
import kotlinx.coroutines.launch

class ProfileViewModel( application: Application) : AndroidViewModel(application) {
    var hasAppUpdate by mutableStateOf(false)
    var appNewVersion by mutableStateOf<FileBean?>(null)
    var sqliteNewVersion by mutableStateOf<FileBean?>(null)
    private val fileNetwork = FileNetWork()
    var message = MutableLiveData("")
    var currentAppVersionName by mutableStateOf("")
    var currentDatabaseVersion by mutableStateOf("")

    init {
        viewModelScope.launch {
            val appVersion =
                application.packageManager.getPackageInfo(application.packageName, 0).versionName
            currentAppVersionName = "$appVersion"
            val dataVersion = DBManager.getInstance().mDB.version
            currentDatabaseVersion = "$dataVersion"
        }
    }

    public fun checkAppUpdate() {
        val version = getApplication<Application>().packageManager.getPackageInfo(getApplication<Application>().packageName, 0).versionCode
        viewModelScope.launch {
            try {
                val bean = fileNetwork.searchArticle("compose_app")
                bean?.data?.let {
                    if (it.version >= version) {
                        appNewVersion = it
                        message.value = "获取到了App新版本"
                        return@launch
                    }
                }
                message.value = "App已经是最新版本"
                appNewVersion = null
            } catch (e: Exception) {
                message.value = "网络请求失败"
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
                        message.value = "获取到了数据库新版本"
                        return@launch
                    }
                }
                sqliteNewVersion = null
                message.value = "数据库已经是最新版本"
            } catch (e: Exception) {
                e.printStackTrace()
                message.value = "网络请求失败"
            }
        }
    }


}