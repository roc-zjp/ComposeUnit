package com.zjp.compose_unit.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.zjp.compose_unit.ComposeItem

class ShareViewModel : ViewModel() {
    var composeItem by mutableStateOf<ComposeItem?>(null)
        private set


    fun addItem(item: ComposeItem) {
        composeItem = item
    }

}