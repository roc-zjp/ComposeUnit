package com.zjp.compose_unit.viewmodel

import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zjp.core_net.FileBean
import com.zjp.core_net.FileNetWork
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    var hasAppUpdate by mutableStateOf(false)
    var newVersion by mutableStateOf<FileBean?>(null)
    private val fileNetwork = FileNetWork()

    fun checkUpdate() {
        viewModelScope.launch {
            val bean = fileNetwork.searchArticle("compose_app")
            newVersion = bean?.data
        }
    }

}